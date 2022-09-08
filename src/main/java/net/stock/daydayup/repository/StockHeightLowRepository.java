/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.StockLowHeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/9/8 9:14
 */
public interface StockHeightLowRepository extends JpaRepository<StockLowHeightEntity,String>, JpaSpecificationExecutor<StockLowHeightEntity> {
}
