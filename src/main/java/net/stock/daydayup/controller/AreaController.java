/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.controller;

import net.stock.daydayup.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:dailm
 * @create at :2022/8/18 8:52
 */
@RestController
@RequestMapping("/v1/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/download")
    public String update(){
        areaService.download();
        return "Ok";
    }
}
