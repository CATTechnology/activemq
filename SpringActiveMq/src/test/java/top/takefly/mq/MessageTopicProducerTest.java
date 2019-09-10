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
public class MessageTopicProducerTest {

    private static final String BROKER_URL = "tcp://119.23.64.69:61616";
    private static final String TOPIC_TEST = "topic_test";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Map<Object, Object> map = new HashMap<>();
        map.put(1 , "zhangsan");
        map.put(2 , "wangwu");
        Session session = connection.createSession(false, 3);

        Topic topic = session.createTopic(TOPIC_TEST);
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(JSON.toJSONString(map));
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("姓名" , "戴灵飞");
            mapMessage.setString("性别" , "男");
            mapMessage.setLong("年龄" , Long.parseLong("20"));
            producer.send(mapMessage);
        }

        connection.close();
        session.close();
    }
}
