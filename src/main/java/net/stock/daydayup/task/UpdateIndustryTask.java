/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.task;

import net.stock.daydayup.service.AreaService;
import net.stock.daydayup.service.ConceptService;
import net.stock.daydayup.service.IndustryService;
import net.stock.daydayup.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author:dailm
 * @create at :2022/8/19 10:49
 */
@Component
public class UpdateIndustryTask {

    @Autowired
    AreaService areaService;
    @Autowired
    ConceptService conceptService;
    @Autowired
    IndustryService industryService;
    @Autowired
    ObjectService objectService;


    /**
     * 每天8点、15点执行
     */
    @Scheduled(cron = "0 0 8,15 * * ?")
    public void areaTask(){
        areaService.download();
    }

    @Scheduled(cron = "0 0 8,15 * * ?")
    public void conceptTask(){
        conceptService.downloadConcept();
    }

    @Scheduled(cron = "0 0 8,15 * * ?")
    public void industryTask(){
        industryService.download();
    }

    @Scheduled(cron = "0 0 8,15 * * ?")
    public void objectTagTask(){
        objectService.refreshStockTag();
    }

    @Scheduled(cron = "0 0 8,15 * * ?")
    public void objectTask(){
        objectService.refreshStockInfo();
    }
}
