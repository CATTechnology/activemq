package top.takefly.mq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.Topic;


/**
 * @program: SpringActiveMq
 * @description: 生产者
 * @author: 戴灵飞
 * @create: 2019-09-09 11:55
 **/
@Component
public class MessageQueueProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queue;

    @Autowired
    private Topic topic;


    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-jms.xml");
        MessageQueueProducer producer = context.getBean("messageQueueProducer", MessageQueueProducer.class);
        while(true){
            producer.jmsTemplate.send(producer.queue ,  session->{
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("MessageProducer生产的消息.....");
                return textMessage;
            });
            Thread.sleep(3000L);
        }

    }
}
