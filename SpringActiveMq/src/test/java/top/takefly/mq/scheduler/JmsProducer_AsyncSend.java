package top.takefly.mq.scheduler;

import org.apache.activemq.*;

import javax.jms.*;
import java.util.UUID;

/**
 * @program: SpringActiveMq
 * @description: 异步发送如何确认消息发送成功?
 * @author: 戴灵飞
 * @create: 2019-09-16 15:44
 **/
public class JmsProducer_AsyncSend {

    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
            "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";

    private static final String QUEUEAsyncSend = "schedulerQueue";

    public static void main(String[] args) throws Exception {
        //首先创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUEAsyncSend);
        MessageProducer producer = session.createProducer(queue);

        for (int i = 0; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("使用异步发送消息------"+ UUID.randomUUID().toString());
            textMessage.setJMSMessageID(UUID.randomUUID().toString());
            String id = textMessage.getJMSMessageID();
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 3000);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 2000);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 3);
            producer.send(textMessage);
        }

        //System.in.read();

        session.close();
        connection.close();
    }
}
