/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.IndustryEntiry;
import net.stock.daydayup.bean.IndustryObjectTagEntity;
import net.stock.daydayup.bean.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/8/18 15:21
 */
public interface IndustryObjectRepository extends JpaRepository<IndustryObjectTagEntity,Long>, JpaSpecificationExecutor<IndustryObjectTagEntity> {
    IndustryObjectTagEntity findByIndustryAndObject(IndustryEntiry industry, ObjectEntity object);
}
