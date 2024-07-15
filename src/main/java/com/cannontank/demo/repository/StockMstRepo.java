package com.cannontank.demo.repository;

import com.cannontank.demo.model.StockMstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMstRepo extends JpaRepository<StockMstEntity,Long> {
}
