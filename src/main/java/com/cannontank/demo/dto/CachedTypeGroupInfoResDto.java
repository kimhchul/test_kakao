package com.cannontank.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@Getter
@Setter
public class CachedTypeGroupInfoResDto {


    private List<Long> stockIdList;    //주식Id 리스트

    private int pageNo; //페이지정보

    private Date lastUpdateDate; //정보갱신일자

}