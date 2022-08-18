/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.dao.ConceptDao;
import net.stock.daydayup.bean.ConceptEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.service.ConceptService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/17 16:18
 */
@Service
public class ConceptServiceImpl implements ConceptService {

    @Autowired
    private ConceptDao conceptDao;

    @Override
    public ConceptEntity save(ConceptEntity entity) {
        try{
            conceptDao.save(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void downloadConcept() {
        int pageSize = 1000;
        int pageIndex = 1;
        for(;;){
            String newUrl = CommonData.listNotion.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getNotionData(HttpUtil.submitGet(newUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                JsonNode dataNode = jsonNode.get("data");
                if(dataNode==null||"null".equals(dataNode)||"".equals(dataNode)||dataNode.isNull()){
                    return;
                }else{
                    pageIndex ++ ;
                }
                for(JsonNode node : dataNode.get("diff")){
                    String code = node.get("f12").asText();
                    String name = node.get("f14").asText();
                    ConceptEntity conceptEntity = new ConceptEntity();
                    conceptEntity.setCode(code);
                    conceptEntity.setName(name);
                    conceptEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    try {
                        save(conceptEntity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getNotionData(String respData){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(CommonData.notionPre);
            respData = respData.substring(preIndex+CommonData.notionPre.length()+1,respData.length()-2);
        }
        return respData;
    }
}
