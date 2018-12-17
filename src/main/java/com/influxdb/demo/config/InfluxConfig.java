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
    public InfluxDB influxDB(){
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
        BiConsumer<Iterable<Point>, Throwable> exceptionHandler = (batch, exception) -> {
            //do something
        };
        BatchOptions options = BatchOptions.DEFAULTS.bufferLimit(500).actions(100).flushDuration(100).jitterDuration(20).exceptionHandler(exceptionHandler);
        influxDB.enableBatch(options);
        influxDB.setDatabase(influxDatabase);
        return influxDB;
    }
//    private static InfluxDB getClient(String databaseName) {
//        InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.199.128:8086", "admin", "admin");
//        Pong pong = influxDB.ping();
//        if (pong != null) {
//            log.info("Pong: " + pong);
//        } else {
//            return null;
//        }
//        Query query = new Query("CREATE DATABASE IF NOT EXISTS " + databaseName, databaseName);
//        influxDB.query(query);
//        return client;
//    }
}
