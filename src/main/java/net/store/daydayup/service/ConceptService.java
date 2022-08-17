/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.service;

import net.store.daydayup.bean.ConceptEntity;

/**
 * @author:dailm
 * @create at :2022/8/17 16:10
 */
public interface ConceptService {
    public ConceptEntity save(ConceptEntity entity);

    public void downloadConcept();
}
