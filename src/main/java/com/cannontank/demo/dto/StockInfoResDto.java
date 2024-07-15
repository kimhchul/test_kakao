package com.cannontank.demo.dto;

import com.cannontank.demo.model.StockMstEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@Getter
@Setter
public class StockInfoResDto {


    private List<StockMstEntity> stockInfoList;    //주식정보리스트

    private Date lastUpdateDate; //정보갱신일자

}