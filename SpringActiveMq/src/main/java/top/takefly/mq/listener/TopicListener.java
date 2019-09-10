package top.takefly.mq.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @program: SpringActiveMq
 * @description: queue监听
 * @author: 戴灵飞
 * @create: 2019-09-09 12:07
 **/
@Component
public class TopicListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        if(null != message && message instanceof TextMessage){
            TextMessage text = (TextMessage)message;
            try {
                System.out.println("监听topic的消息:"+text.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
