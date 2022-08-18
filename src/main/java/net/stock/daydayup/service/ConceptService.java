/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service;

import net.stock.daydayup.bean.ConceptEntity;

/**
 * @author:dailm
 * @create at :2022/8/17 16:10
 */
public interface ConceptService {
    public ConceptEntity save(ConceptEntity entity);

    public void downloadConcept();
}
