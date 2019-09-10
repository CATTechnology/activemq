package top.takefly.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.jms.Queue;
import javax.jms.Topic;
import java.util.UUID;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-09-09 18:40
 **/
@Component
public class MessageProducer {

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Scheduled(fixedDelay = 3000L)
    public void sendMessageQueue(){
        jmsMessagingTemplate.convertAndSend(queue , "springboot生产者queue发送:"+ UUID.randomUUID().toString());
        System.out.println("queue发送成功.......");
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendMessageTopic(){
        jmsMessagingTemplate.convertAndSend(topic , "springboot生产者topic发送:"+ UUID.randomUUID().toString());
        System.out.println("topic发送成功.......");
    }
}
