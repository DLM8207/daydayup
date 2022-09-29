/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.controller;

import lombok.extern.slf4j.Slf4j;
import net.stock.daydayup.bean.ObjectEntity;
import net.stock.daydayup.service.EasyMoneyApiService;
import net.stock.daydayup.service.HxtcService;
import net.stock.daydayup.service.ObjectService;
import net.stock.daydayup.service.SouhuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:dailm
 * @create at :2022/6/21 20:30
 */
@RestController
@RequestMapping("/v1/stock")
@Slf4j
public class StockInfoController {

    @Autowired
    ObjectService objectService;
    @Autowired
    SouhuStockService souhuStockService;
    @Autowired
    EasyMoneyApiService easyMoneyApiService;
    @Autowired
    HxtcService hxtcService;

    @GetMapping("/{code}")
    public String getStock(@PathVariable(name = "code") String code){
        return "Ok";
    }

    @GetMapping("/download")
    public String listStock(){
        objectService.refreshStockInfo();
        return "Ok";
    }

    @PostMapping("/tag")
    public String updateStockTag(){
        objectService.refreshStockTag();
        return "Ok";
    }

    @PostMapping("/download/sohu/{year}")
    public String downloadStockValue(@PathVariable int year){
        souhuStockService.downloadHistoryData(year);
        return "Ok";
    }

    @PostMapping("/download/now")
    public String downloadNowData(){
        easyMoneyApiService.downloadTodayValue();
        return "Ok";
    }

    @GetMapping("/download/hxtc")
    public String downloadHxtc(@RequestParam(name="day",required = false)String day,@RequestParam(name = "code",required = false)String code,@RequestParam(name="keyword",required = false)String keyword){
        hxtcService.updateHxtc(day,code,keyword);
        return "Ok";
    }

}
