package com.influxdb.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KafkaConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
//        // 替代properties.yml中的配置
//        System.setProperty("topicName", logicTopicName);
//        log.info("#########  system config topic:{} ########", logicTopicName);
    }
}