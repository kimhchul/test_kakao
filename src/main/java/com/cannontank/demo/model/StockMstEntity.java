package com.cannontank.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(indexes = @Index(name = "idx_stock_mst", columnList = "code"))
@Getter
@Setter
public class StockMstEntity {

    //Csv example
    //id,code,name,price
    //1,005930,삼성전자,61500
    public StockMstEntity(Long id, String code, String name, Long price)
    {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;

    }

    @Id
    private Long id;

    private String code;

    private String name;

    private Long price;


    protected StockMstEntity() {}



}