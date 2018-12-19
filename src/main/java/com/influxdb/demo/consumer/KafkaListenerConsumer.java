package com.influxdb.demo.consumer;

import com.influxdb.demo.config.InfluxConfig;
import com.influxdb.demo.core.ApplicationContextHelper;
import com.influxdb.demo.service.InfluxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lianghaiyang
 * @date 2018/12/19
 * 第二种方式配置消费者
 *
 */
@Slf4j
public class KafkaListenerConsumer implements MessageListener<String, String> {
    private InfluxService influxService;
    private InfluxConfig influxConfig;
    KafkaListenerConsumer() {
        influxService = ApplicationContextHelper.getBean(InfluxService.class);
        influxConfig = ApplicationContextHelper.getBean(InfluxConfig.class);
    }
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        //根据不同的主题进行消费
        String topic = data.topic();
        switch (topic) {
            case "topic_test":
                influxService.createInfluxDatabase(influxConfig.getInfluxDatabase());
                List<String> records = Arrays.stream(data.value().split(",")).collect(Collectors.toList());
                influxService.writeRecord("table8", records.get(0), records.get(1), records.get(2));
                log.info("--------------topic_test---------------"+data.value());
                break;
            case "topic_test1":
                log.info("--------------topic_test1---------------"+data.value());
                break;
            default:
                log.info("存在未监听的topic");
                break;
        }

    }
}
