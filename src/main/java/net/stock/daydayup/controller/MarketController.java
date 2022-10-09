/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.controller;

import net.stock.daydayup.task.EmotionMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author:dailm
 * @create at :2022/10/2 18:33
 */
@RestController
@RequestMapping("/v1/market")
public class MarketController {

    @Autowired
    private EmotionMonitor emotionMonitor;

    @GetMapping("/speculate")
    public String getSpeculateEmotionBean(){
        return emotionMonitor.getMarketEmotion().toString();
    }
}
