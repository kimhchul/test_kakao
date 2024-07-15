package com.cannontank.demo.serivce;

import com.cannontank.demo.dto.CachedTypeGroupInfoResDto;
import com.cannontank.demo.repository.StockMstRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CacheRepoService {

    @Autowired
    StockMstRepo stockMstRepo;


    /**
     * 무조건 cache 정보를 갱신한다
     * @param cacheKey
     * @param idLists
     * @param pageNo
     * @return
     */
    @CachePut(value = "result", key = "#cacheKey")
    public CachedTypeGroupInfoResDto putCacheIdListGroupByType(String cacheKey, List<Long> idLists, int pageNo)
    {
        log.info("cacheKey {} created. idLists size {} ", cacheKey, idLists.size());

        CachedTypeGroupInfoResDto dto = new CachedTypeGroupInfoResDto();
        dto.setStockIdList(idLists);
        dto.setLastUpdateDate(new Date());
        dto.setPageNo(pageNo);
        return dto;
    }

    /**
     * 캐시정보를 GET 만 한다. (캐시된 내용이 없으면 NULL)
     * @param cacheKey
     * @return
     */
    @Cacheable(value = "result", key = "#cacheKey")
    public CachedTypeGroupInfoResDto getCacheIdListGroupByType(String cacheKey)
    {
        return null;
    }




}
