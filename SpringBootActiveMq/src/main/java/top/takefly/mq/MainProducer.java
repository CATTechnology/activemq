package top.takefly.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: SpringActiveMq
 * @description: 启动类
 * @author: 戴灵飞
 * @create: 2019-09-09 18:10
 **/
@SpringBootApplication
@EnableScheduling
public class MainProducer {

    public static void main(String[] args) {
        SpringApplication.run(MainProducer.class , args);
    }
}
