/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.HxtcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/9/15 9:33
 */
public interface HxtcRepository extends JpaRepository<HxtcEntity,Long>, JpaSpecificationExecutor<HxtcEntity> {
}
