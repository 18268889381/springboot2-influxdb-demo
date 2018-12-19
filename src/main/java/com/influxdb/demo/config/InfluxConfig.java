package com.influxdb.demo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiConsumer;

/**
 * @author lianghaiyang
 * @date 2018/12/13
 */
@Slf4j
@Configuration
@Setter
@Getter
@ToString
public class InfluxConfig {
    @Value("${spring.influx.database}")
    private String influxDatabase;
    @Value("${spring.influx.url}")
    private String influxUrl;
    @Value("${spring.influx.user}")
    private String influxUser;
    @Value("${spring.influx.password}")
    private String influxPassword;

    @Bean
    public InfluxDB influxDB() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
        BiConsumer<Iterable<Point>, Throwable> exceptionHandler = (batch, exception) -> {
            //批量插入失败
            log.error("influxDB 入库失败，请查看");
            exception.printStackTrace();
        };
        // 设置批量插入，满足条件1000条开始插入，每一秒插入一次
        BatchOptions options = BatchOptions.DEFAULTS.bufferLimit(5000).actions(100).flushDuration(1000).jitterDuration(20).exceptionHandler(exceptionHandler);
        //开启批量插入
        influxDB.enableBatch(options);
        influxDB.setDatabase(influxDatabase);
        return influxDB;
    }
}
