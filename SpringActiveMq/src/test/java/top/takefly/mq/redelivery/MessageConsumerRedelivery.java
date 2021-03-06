package top.takefly.mq.redelivery;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 异步发送如何确认消息发送成功?
 * @author: 戴灵飞
 * @create: 2019-09-16 15:44
 **/
public class MessageConsumerRedelivery {

    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
            "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";

    private static final String QUEUEAsyncSend = "redeliveryQueue";

    public static void main(String[] args) throws Exception {
        //首先创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUEAsyncSend);
        MessageConsumer consumer = session.createConsumer(queue);


        Message message = consumer.receive();
        while (message != null && message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
            //等待4秒
            message = consumer.receive(1000L);
        }

        //事务未提交
//        session.commit();

        session.close();
        connection.close();
    }
}
