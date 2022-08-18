/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.dao.impl;

import net.stock.daydayup.bean.IndustryEntiry;
import net.stock.daydayup.repository.IndustryRepository;
import net.stock.daydayup.dao.IndustryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author:dailm
 * @create at :2022/8/17 17:19
 */
@Repository
public class IndustryDaoImpl implements IndustryDao {

    @Autowired
    private IndustryRepository industryRepository;

    @Override
    public IndustryEntiry save(IndustryEntiry entiry) {
        return industryRepository.save(entiry);
    }
}
