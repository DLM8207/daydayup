/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.repository;

import net.store.daydayup.bean.ConceptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/8/17 15:59
 */
public interface ConceptRepository extends JpaRepository<ConceptEntity,Long>, JpaSpecificationExecutor<ConceptEntity> {
}
