package top.takefly.mq.serializableTopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageTopicConsumerTest {

//    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
//    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String BROKER_URL = "auto+nio://192.168.174.129:61608";
    private static final String TOPIC_PERSIST = "Topic_persist";

    public static void main(String[] args) throws Exception {
        System.out.println("消费者2号");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("c2");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_PERSIST);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "Remark");
        connection.start();

        Message message = topicSubscriber.receive();

        while(null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println("收到的消息:"+textMessage.getText());
            message = topicSubscriber.receive(3000L);
        }

        connection.close();
        session.close();
    }
}
