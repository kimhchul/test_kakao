package com.cannontank.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StartController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("데이터 초기로드 테스트")
    void init_data_checkTest() {

    }

    @Test
    @DisplayName("주식정보 생성 테스트")
    void createStockInfoTest() {

    }


    @Test
    @DisplayName("주식정보조회 테스트 (타입별)")
    void getStockListTest() {

    }





}
