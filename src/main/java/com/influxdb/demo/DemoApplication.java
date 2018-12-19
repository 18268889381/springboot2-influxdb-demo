package com.influxdb.demo;

import com.influxdb.demo.core.ApplicationContextHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
/**
 * @author  lianghaiyang 2018/12/19 13:53
 */
@SpringBootApplication
public class DemoApplication {
    @Resource
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void listen() {
        ApplicationContextHelper.setApplicationContext(applicationContext);
    }
}

