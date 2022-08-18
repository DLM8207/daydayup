/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.IndustryEntiry;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.dao.IndustryDao;
import net.stock.daydayup.service.IndustryService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/17 17:18
 */
@Service
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    private IndustryDao industryDao;

    @Override
    public void download() {
        int pageSize = 1000;
        int pageIndex = 1;
        for(;;){
            String newUrl = CommonData.listIndustry.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getIndustryData(HttpUtil.submitGet(newUrl));
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
                    IndustryEntiry industryEntiry = new IndustryEntiry();
                    industryEntiry.setCode(code);
                    industryEntiry.setName(name);
                    industryEntiry.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    try {
                        industryDao.save(industryEntiry);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getIndustryData(String respData){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(CommonData.industryPre);
            respData = respData.substring(preIndex+CommonData.industryPre.length()+1,respData.length()-2);
        }
        return respData;
    }
}
