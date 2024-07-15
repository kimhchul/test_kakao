package com.cannontank.demo.serivce;

import com.cannontank.demo.dto.StockInfoResDto;
import com.cannontank.demo.dto.CachedTypeGroupInfoResDto;
import com.cannontank.demo.model.StockMstEntity;
import com.cannontank.demo.repository.StockMstRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StockInfoService {

    /**
     * 태그별 type
     *      favorite    : 인기
     *      up          : 상승
     *      down        : 하락
     *      count       : 거래량
     */
    
    static final String [] typeArray = {"favorite", "up", "down", "count"};

    @Autowired
    StockMstRepo stockMstRepo;

    @Autowired
    CacheRepoService cacheRepoService;

    /**
     * 주식정보 csv 로드 & H2 DB INSERT
     * @return
     */
    public int makeStockMst() {
        List<StockMstEntity> saveList = new ArrayList<StockMstEntity>();

        try {
            ClassPathResource resource = new ClassPathResource("static/SampleData.csv");

            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String csvText = new String(bdata, StandardCharsets.UTF_8);
            String [] readLine = csvText.split("\n");   //row분리


            int count =0;
            for(String s : readLine)
            {
                if(count == 0){
                    //header skip
                    count++;
                    continue;

                }

                String [] rowdata = s.split(",");   //speratior , 분리(column)
                /**
                 * 순서대로
                 * [0] id
                 * [1] code
                 * [2] name
                 * [3] price
                 */

                StockMstEntity st = new StockMstEntity(Long.parseLong(rowdata[0]), rowdata[1], rowdata[2],Long.parseLong(rowdata[3]) );
                saveList.add(st);
            }
        } catch (Exception e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

        stockMstRepo.saveAll(saveList);

        log.info("saveList size {} insert.", saveList.size());

        return saveList.size();
    }



    /**
     * 타입별 주식정보그룹 생성
     *  cache에만 저장함
     * @param
     * @return
     */
    public List<String> createStockInfoGroupByType()
    {

        long totalCnt = stockMstRepo.count();

        List<Long> pagedIdLists = new ArrayList<Long>();
        //전체 index를 list 추가
        for(long index=1L ; index <= totalCnt; index++)
        {
            pagedIdLists.add(index);
        }


        List<String> cacheKeyList = new ArrayList<String>();

        /**
         *
         * 별도의 page 처리없이 cachekey 값내 pageNo 값 추가
         * pageNo 1 = 0 ~ 19 (pageSize)
         * pageNo 2 = 20 ~ 39
         * page Last = ~ stockMstRepo.count()
         *
         */
        for(String type: typeArray)
        {
            int pageNo = 1;
            int pageSize = 20;

            int startidx = 0;
            int endidx =0;

            //TYPE 별로 다양성 을 위하여 랜덤하게 리스트 섞기.
            Collections.shuffle(pagedIdLists);

            /**
             * cache key pattern
             * cache_{type}_{pageNo}
             */
            for (int i = 0; i < totalCnt / pageSize; i++) {
                startidx = (pageNo - 1) * pageSize;

                endidx = startidx + (pageSize - 1);

                if (endidx > (totalCnt - 1))
                    endidx = (int) (totalCnt - 1);//maxIndex

                log.info("startidx  {} , endidx {}, total {}", startidx, endidx, totalCnt);


                String cacheKey = String.format("cache_%s_%s", type, pageNo);
                cacheKeyList.add(cacheKey);

                CachedTypeGroupInfoResDto resDto = cacheRepoService.putCacheIdListGroupByType(cacheKey
                        , pagedIdLists.subList(startidx, endidx), pageNo);


                pageNo++;
            }

        }


        log.info("cacheKeyList size {}. created.", cacheKeyList.size());

        return cacheKeyList;


    }


    /**
     * 타입별 주식정보 조회
     *  cache 에서 read만 함
     * @param type
     * @param pageNo
     * @return
     */
    public StockInfoResDto getStockInfoByType(String type, int pageNo) {
        StockInfoResDto result = new StockInfoResDto();

        //default.
        if (pageNo == 0) {
            pageNo = 1;
        }

        String cacheKey = String.format("cache_%s_%s", type, pageNo);

        CachedTypeGroupInfoResDto resDto = cacheRepoService.getCacheIdListGroupByType(cacheKey);

        //빈리스트 제공
        if (resDto == null) {
            return result;
        }

        List<StockMstEntity> list = stockMstRepo.findAllById(resDto.getStockIdList());

        result.setStockInfoList(list);
        result.setLastUpdateDate(resDto.getLastUpdateDate());


        return result;
    }
}


