package com.cannontank.demo;

import com.cannontank.demo.serivce.StockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private static final Logger LOG =
            LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    StockInfoService fileLoadService;


    @Override
    public void run(String...args) throws Exception {
        
        //데이터적재
        fileLoadService.makeStockMst();
    }
}