/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * @author:dailm
 * @create at :2022/8/18 8:38
 */
public interface AreaRepository extends JpaRepository<AreaEntity,Integer>, JpaSpecificationExecutor<AreaEntity> {
    List<AreaEntity> findByCodeLike(String code);
    List<AreaEntity> findByName(String name);
}
