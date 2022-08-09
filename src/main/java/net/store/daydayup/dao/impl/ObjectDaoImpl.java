/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.dao.impl;

import net.store.daydayup.bean.ObjectEntity;
import net.store.daydayup.dao.ObjectDao;
import net.store.daydayup.repository.ObjectEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author:dailm
 * @create at :2022/8/2 17:40
 */
@Repository
public class ObjectDaoImpl implements ObjectDao {
    @Autowired
    private ObjectEntityRepository repository;

    @Override
    public ObjectEntity save(ObjectEntity entity) {
        return repository.save(entity);
    }
}
