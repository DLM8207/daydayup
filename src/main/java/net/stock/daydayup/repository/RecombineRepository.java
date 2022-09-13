/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.RecombineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;

/**
 * @author:dailm
 * @create at :2022/9/13 14:46
 */
public interface RecombineRepository extends JpaRepository<RecombineEntity,Long>, JpaSpecificationExecutor<RecombineEntity> {
    RecombineEntity findByDayAndCode(Date day,String code);
}
