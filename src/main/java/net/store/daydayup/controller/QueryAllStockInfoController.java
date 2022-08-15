/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.controller;

import lombok.extern.slf4j.Slf4j;
import net.store.daydayup.bean.ObjectEntity;
import net.store.daydayup.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:dailm
 * @create at :2022/6/21 20:30
 */
@RestController
@RequestMapping("/v1/stock")
@Slf4j
public class QueryAllStockInfoController {

    @Autowired
    ObjectService objectService;

    @GetMapping("/list")
    public List<ObjectEntity> listStock(){
        objectService.refreshStockInfo();
        return new ArrayList<ObjectEntity>();
    }



}