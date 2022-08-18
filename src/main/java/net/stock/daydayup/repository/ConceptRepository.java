/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.ConceptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/8/17 15:59
 */
public interface ConceptRepository extends JpaRepository<ConceptEntity,Long>, JpaSpecificationExecutor<ConceptEntity> {
    List<ConceptEntity> findByName(String name);
}
