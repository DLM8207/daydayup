/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.dao.impl;

import net.store.daydayup.bean.IndustryEntiry;
import net.store.daydayup.dao.IndustryDao;
import net.store.daydayup.repository.IndustryRepository;
import net.store.daydayup.service.IndustryService;
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
