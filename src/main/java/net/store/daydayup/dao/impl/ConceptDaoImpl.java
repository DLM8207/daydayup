/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.dao.impl;

import net.store.daydayup.bean.ConceptEntity;
import net.store.daydayup.dao.ConceptDao;
import net.store.daydayup.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author:dailm
 * @create at :2022/8/17 16:02
 */
@Repository
public class ConceptDaoImpl implements ConceptDao {

    @Autowired
    private ConceptRepository conceptRepository;

    @Override
    public ConceptEntity save(ConceptEntity entity) {
        return conceptRepository.save(entity);
    }
}
