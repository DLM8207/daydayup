/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.dao.impl;

import net.stock.daydayup.bean.AreaEntity;
import net.stock.daydayup.dao.AreaDao;
import net.stock.daydayup.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author:dailm
 * @create at :2022/8/18 8:44
 */
@Repository
public class AreaDaoImpl implements AreaDao {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public AreaEntity save(AreaEntity entity) {
        return areaRepository.save(entity);
    }
}
