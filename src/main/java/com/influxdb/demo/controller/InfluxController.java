package com.influxdb.demo.controller;

import com.influxdb.demo.service.InfluxService;
import org.influxdb.dto.QueryResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lianghaiyang
 * @date 2018/11/22
 */
@RestController
public class InfluxController {
    @Resource
    private InfluxService influxService;

    /**
     * 读取excel中的数据进行发送
     */
    @PostMapping("/influx/query")
    public QueryResult sendMsg(String sql) {
        return influxService.querySql(sql);
    }
}
