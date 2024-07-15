package com.cannontank.demo.controller;

import com.cannontank.demo.dto.DefaultResDto;
import com.cannontank.demo.dto.StockInfoResDto;
import com.cannontank.demo.model.StockMstEntity;
import com.cannontank.demo.serivce.StockInfoService;
import com.cannontank.demo.util.ResponseMessage;
import com.cannontank.demo.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class StartController {

    @Autowired
    StockInfoService stockInfoService;


    /**
     * 데이터 초기화
     *  CSV -> h2 데이터 적재
     * @return
     */
    @GetMapping("/init")
    public ResponseEntity<String> init() {
        int insertCnt = stockInfoService.makeStockMst();
        return new ResponseEntity(DefaultResDto.res(StatusCode.OK, ResponseMessage.SUCCESS, insertCnt), HttpStatus.OK);
    }


    /**
     * 주식정보 데이터 생성
     *  type + pageNo 로 모든 주식정보 제공 리스트 생성 (갱신)
     * @return
     */
    @GetMapping("/createStockInfo")
    public ResponseEntity cachePut() {
        List<String> createdCacheKeyList = stockInfoService.createStockInfoGroupByType();
        return new ResponseEntity(DefaultResDto.res(StatusCode.OK, ResponseMessage.SUCCESS, createdCacheKeyList), HttpStatus.OK);
    }


    /**
     * 주식정보 조회 (type별)
     * @param type (favorite|up|down|count)
     * @param pageNo (1 =< pageNo)
     * @return
     */
    @GetMapping("/{type}/getStockList")
    public ResponseEntity getStockList(@PathVariable  String type, int pageNo) {

        StockInfoResDto responseDto = stockInfoService.getStockInfoByType(type, pageNo);
        return new ResponseEntity(DefaultResDto.res(StatusCode.OK, ResponseMessage.SUCCESS, responseDto), HttpStatus.OK);
    }
}


