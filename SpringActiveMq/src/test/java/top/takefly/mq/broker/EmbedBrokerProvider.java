package top.takefly.mq.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.broker.BrokerService;
import top.takefly.mq.MessageTopicConsumerTest;

/**
 * @program: SpringActiveMq
 * @description: broker生产者
 * @author: 戴灵飞
 * @create: 2019-09-07 20:33
 **/
public class EmbedBrokerProvider {

    private static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector(BROKER_URL);
        brokerService.start();


    }
}
