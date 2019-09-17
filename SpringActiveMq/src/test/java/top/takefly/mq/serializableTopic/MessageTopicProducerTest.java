package top.takefly.mq.serializableTopic;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageTopicProducerTest {

    //    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String BROKER_URL = "auto+nio://192.168.174.129:61608";
    //    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_PERSIST = "Topic_persist";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_PERSIST);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        for (int i = 0; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("生产者生产的消息ID" + UUID.randomUUID().toString().replace(" ", ""));
            producer.send(textMessage);
        }

        session.commit();
        connection.close();
        session.close();
    }
}
