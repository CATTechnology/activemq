package top.takefly.mq.redelivery;

import org.apache.activemq.*;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 异步发送如何确认消息发送成功?
 * @author: 戴灵飞
 * @create: 2019-09-16 15:44
 **/
public class MessageProducerRedelivery {

    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
            "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";

    private static final String QUEUEAsyncSend = "redeliveryQueue";

    public static void main(String[] args)throws Exception {
        //首先创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(2);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection =  activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUEAsyncSend);
        MessageProducer producer =  session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 0; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("使用异步发送消息------"+(i+1));
            textMessage.setJMSMessageID(i+"");
            producer.send(textMessage);
        }

        session.commit();
        session.close();
        connection.close();
    }
}
