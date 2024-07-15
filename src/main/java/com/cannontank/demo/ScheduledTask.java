package com.cannontank.demo;

import com.cannontank.demo.serivce.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    StockInfoService fileLoadService;


    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("mm:ss:SSS");


    /**
     * 10초마다 주식정보 집계 갱신
     */
    @Scheduled(fixedRate = 10000)
    public void fixedRate() {
        //주식투자정보 집계 호출
        fileLoadService.createStockInfoGroupByType();


    }
}
