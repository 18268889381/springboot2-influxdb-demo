package com.influxdb.demo.consumer;

import com.influxdb.demo.config.InfluxConfig;
import com.influxdb.demo.service.InfluxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lianghaiyang 2018/11/22 10:37
 */
@Slf4j
public class KafkaConsumerListener {
    @Resource
    private InfluxService influxService;
    @Resource
    private InfluxConfig influxConfig;

    /**
     * 消费消息统一处理
     */
    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        influxService.createInfluxDatabase(influxConfig.getInfluxDatabase());
        List<String> records = Arrays.stream(record.value().toString().split(",")).collect(Collectors.toList());
        influxService.writeRecord("table7", records.get(0), records.get(1), records.get(2));
    }
}