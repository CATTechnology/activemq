package top.takefly.mq.async;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;

/**
 * @program: SpringActiveMq
 * @description: 异步发送如何确认消息发送成功?
 * @author: 戴灵飞
 * @create: 2019-09-16 15:44
 **/
public class JmsProducer_AsyncSend {

    private static final String BROKER_URL = "failover:(auto+nio://192.168.174.133:61608," +
            "auto+nio://192.168.174.129:61608,auto+nio://192.168.174.132:61608)?randomize=false";

    private static final String QUEUEAsyncSend = "asyncSendQueue";

    public static void main(String[] args)throws Exception {
        //首先创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
        connection.start();
        //异步发送
        connection.setUseAsyncSend(true);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUEAsyncSend);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);
        for (int i = 0; i < 20000; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("使用异步发送消息------"+(i+1));
            textMessage.setJMSMessageID(i+"");
            String id = textMessage.getText();
            producer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("编号为:"+id+"的消息发送成功....");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println("编号为:"+id+"的消息发送失败!!!!");
                }
            });
        }

        session.close();
        connection.close();
    }
}
