/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.repository;

import net.store.daydayup.bean.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/8/2 17:32
 */
public interface ObjectEntityRepository extends JpaRepository<ObjectEntity,Long>, JpaSpecificationExecutor<ObjectEntity> {
}
