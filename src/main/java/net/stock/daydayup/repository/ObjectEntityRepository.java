/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/8/2 17:32
 */
public interface ObjectEntityRepository extends JpaRepository<ObjectEntity,Long>, JpaSpecificationExecutor<ObjectEntity> {
    @Query(value="select code from object",nativeQuery = true)
    List<String> findAllCode();
}
