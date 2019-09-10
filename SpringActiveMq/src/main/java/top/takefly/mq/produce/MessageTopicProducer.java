package top.takefly.mq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.UUID;


/**
 * @program: SpringActiveMq
 * @description: 生产者
 * @author: 戴灵飞
 * @create: 2019-09-09 11:55
 **/
@Component
public class MessageTopicProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queue;

    @Autowired
    private Destination topic;


    public static void main(String[] args) throws InterruptedException {
        //from join on where  group by having select distinct order by
//        ApplicationContext context = new ClassPathXmlApplicationContext("application-jms.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\javaEE\\IDEA_Project\\mq\\SpringActiveMq\\src\\main\\resources\\application-jms.xml");
        MessageTopicProducer producer = context.getBean("messageTopicProducer", MessageTopicProducer.class);
        while(true){
            producer.jmsTemplate.setDeliveryMode(2);
            producer.jmsTemplate.send(session->{
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("MessageProducer生产的消息....."+ UUID.randomUUID().toString());
                return textMessage;
            });
            Thread.sleep(3000L);
        }

    }
}
