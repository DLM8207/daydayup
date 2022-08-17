/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.controller;

import net.store.daydayup.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:dailm
 * @create at :2022/8/17 17:26
 */
@RestController
@RequestMapping("/v1/industry")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    @GetMapping("/download")
    public String update(){
        industryService.download();
        return "Ok";
    }
}
