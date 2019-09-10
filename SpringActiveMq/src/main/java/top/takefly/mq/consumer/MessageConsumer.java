package top.takefly.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 消费者
 * @author: 戴灵飞
 * @create: 2019-09-09 11:56
 **/
@Component
public class MessageConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queue;

    @Autowired
    private Topic topic;


    public static void main(String[] args) throws JMSException {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-jms.xml");
        MessageConsumer consumer = context.getBean("messageConsumer", MessageConsumer.class);
        TextMessage receive = (TextMessage) consumer.jmsTemplate.receive();
        System.out.println(receive.getText());
    }
}
