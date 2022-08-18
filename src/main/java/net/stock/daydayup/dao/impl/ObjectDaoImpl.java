/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.dao.impl;

import net.stock.daydayup.bean.ObjectEntity;
import net.stock.daydayup.dao.ObjectDao;
import net.stock.daydayup.repository.ObjectEntityRepository;
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
