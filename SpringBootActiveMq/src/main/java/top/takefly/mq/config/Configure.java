package top.takefly.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

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

    @Value("${activemq.topic}")
    private String topic;

    @Bean
    public Topic getTopic(){
        return new ActiveMQTopic(topic);
    }

    @Bean
    public Queue getQueue(){
        return new ActiveMQQueue(queue);
    }

}
