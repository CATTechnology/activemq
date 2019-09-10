package top.takefly.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-08-29 20:48
 **/
public class MessageConsumer2Test {

    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String QUEUE_TEST = "queue_test";

    public static void main(String[] args) throws Exception {
        System.out.println("消费者2号");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, 3);
        Queue queue = session.createQueue(QUEUE_TEST);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                System.out.println(text);
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
