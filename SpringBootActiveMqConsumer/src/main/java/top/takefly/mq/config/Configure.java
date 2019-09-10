package top.takefly.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @program: SpringActiveMq
 * @description: 加载配置
 * @author: 戴灵飞
 * @create: 2019-09-09 18:24
 **/
@Component
@EnableJms
public class Configure {

    @Value("${activemq.queue}")
    private String queue;

    @Value("${activemq.queue}")
    private String topic;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Topic getTopic(){
        return new ActiveMQTopic(topic);
    }

    @Bean
    public Queue getQueue(){
        return new ActiveMQQueue(queue);
    }

    @Bean("topicJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory getTopicConnectionFactory(){
        DefaultJmsListenerContainerFactory jmsListenerFactory = new DefaultJmsListenerContainerFactory();
        //当前为topic容器工厂
        jmsListenerFactory.setConnectionFactory(connectionFactory);
        jmsListenerFactory.setPubSubDomain(true);
        return jmsListenerFactory;
    }

    @Bean("queueJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory getQueueConnectionFactory(){
        DefaultJmsListenerContainerFactory jmsListenerFactory = new DefaultJmsListenerContainerFactory();
        //当前为topic容器工厂
        jmsListenerFactory.setConnectionFactory(connectionFactory);
        jmsListenerFactory.setPubSubDomain(false);
        return jmsListenerFactory;
    }

}
