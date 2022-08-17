/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.repository;

import net.store.daydayup.bean.IndustryEntiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:dailm
 * @create at :2022/8/17 17:15
 */
public interface IndustryRepository extends JpaRepository<IndustryEntiry,Integer>, JpaSpecificationExecutor<IndustryEntiry> {
}
