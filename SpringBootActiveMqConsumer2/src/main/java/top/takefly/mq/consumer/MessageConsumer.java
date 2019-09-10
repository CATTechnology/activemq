package top.takefly.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 * @program: SpringActiveMq
 * @description: 消息生产者
 * @author: 戴灵飞
 * @create: 2019-09-09 18:40
 **/
@Component
public class MessageConsumer {

    @Autowired
    private Queue queue;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @JmsListener(destination="${activemq.topic}")
    public void sendMessage1(TextMessage message) throws JMSException {
        System.out.println(message.getText());
    }

    @JmsListener(destination="${activemq.queue}" , containerFactory = "queueJmsListenerContainerFactory")
    public void sendMessage2(TextMessage message) throws JMSException {
        System.out.println(message.getText());
    }
}
