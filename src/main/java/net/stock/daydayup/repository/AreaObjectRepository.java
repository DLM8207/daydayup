/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.AreaEntity;
import net.stock.daydayup.bean.AreaObjectTagEntigy;
import net.stock.daydayup.bean.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/8/18 10:34
 */
public interface AreaObjectRepository extends JpaRepository<AreaObjectTagEntigy,Long>, JpaSpecificationExecutor<AreaObjectTagEntigy> {
    AreaObjectTagEntigy findByAreaAndObject(AreaEntity area, ObjectEntity object);
}
