/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.StockValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/8/31 14:12
 */
public interface StockValueRepository extends JpaRepository<StockValueEntity,String>, JpaSpecificationExecutor<StockValueEntity> {

    @Query(value = "select day from object_day_value where stockcode=:code and day >=cast(:day as date)",nativeQuery = true)
    List<String> findDayByCodeAndYear(String code, String day);
}
