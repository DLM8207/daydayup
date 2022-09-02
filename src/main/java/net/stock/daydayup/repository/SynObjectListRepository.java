/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.SynObjectListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/9/1 14:20
 */
public interface SynObjectListRepository extends JpaRepository<SynObjectListEntity,Long>, JpaSpecificationExecutor<SynObjectListEntity> {
}
