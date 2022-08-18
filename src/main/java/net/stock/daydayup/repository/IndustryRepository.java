/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.repository;

import net.stock.daydayup.bean.IndustryEntiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/8/17 17:15
 */
public interface IndustryRepository extends JpaRepository<IndustryEntiry,Integer>, JpaSpecificationExecutor<IndustryEntiry> {
    List<IndustryEntiry> findByName(String name);
}
