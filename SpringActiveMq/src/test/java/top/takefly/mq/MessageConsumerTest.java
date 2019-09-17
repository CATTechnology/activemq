package top.takefly.mq;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageConsumerTest {

    /**
     * activemq.xml里面的核心配置：<transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61608?maximumConnections=1000
     * &amp;wireFormat.maxFrameSize=104857600&amp;org.apache.activemq.transport.nio.SelectorManager.corelPoolSize=20
     * &amp;org.apache.activemq.transport.nio.SelectorManager.maximumPoolSize=50"/>
     */

//    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
            "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";
    private static final String QUEUE_TEST = "nio_test";

    public static void main(String[] args) throws Exception {
        System.out.println("消费者1号");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, 3);
        Queue queue = session.createQueue(QUEUE_TEST);
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
           if(null != message && message instanceof TextMessage){
               TextMessage textMessage = (TextMessage) message;
               try {
                   String text = textMessage.getText();
                   System.out.println(text);
               } catch (JMSException e) {
                   e.printStackTrace();
               }
           }
        });
        //事务提交
        session.commit();
        //等待
        System.in.read();
        connection.close();
        session.close();
    }
}
