package top.takefly.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageTopicConsumerTest {

    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String TOPIC_TEST = "topic_test";

    public static void main(String[] args) throws Exception {
        System.out.println("消费者1号");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, 3);
        Topic topic = session.createTopic(TOPIC_TEST);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            try {
                if(null != message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("textMessage:"+text);
                }

                if(null != message && message instanceof MapMessage){
                    MapMessage mapMessage = (MapMessage)message;
                    System.out.println("姓名:"+mapMessage.getString("姓名"));
                    System.out.println("性别:"+mapMessage.getString("性别"));
                    System.out.println("年龄:"+mapMessage.getString("年龄"));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //等待
        System.in.read();

        connection.close();
        session.close();
    }
}
