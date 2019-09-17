package top.takefly.mq;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageProducerTest {

//    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
        "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";
    private static final String QUEUE_TEST = "nio_test";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(true, 3);
        Queue queue = session.createQueue(QUEUE_TEST);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage();
            //设置消息持久化
            //消息持久化:一条持久化消息，会发送‘一次仅仅一次’,意味着即使JMS服务器故障，该消息也不会丢失，服务器恢复后再次传递
            //非持久化的消息:一条非持久化消息，最多发送一次，当服务器出现故障，该消息永久丢失
            textMessage.setText("persistence JDBC:"+i+1);
            producer.send(textMessage);
        }
        connection.close();
        session.close();
    }
}
