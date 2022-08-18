/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.ConceptEntity;
import net.stock.daydayup.bean.ConceptObjectTagEntity;
import net.stock.daydayup.bean.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/8/18 15:20
 */
public interface ConceptObjectRepository extends JpaRepository<ConceptObjectTagEntity,Long>, JpaSpecificationExecutor<ConceptObjectTagEntity> {
    ConceptObjectTagEntity findByConceptAndObject(ConceptEntity concept, ObjectEntity object);
}
