/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.controller;

import net.store.daydayup.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:dailm
 * @create at :2022/8/17 15:59
 */
@RestController
@RequestMapping("/v1/concept")
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @GetMapping("/download")
    public String update(){
        conceptService.downloadConcept();
        return "ok";
    }
}
