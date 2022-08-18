/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.AreaEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.dao.AreaDao;
import net.stock.daydayup.service.AreaService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/18 8:45
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public void download() {
        int pageSize = 1000;
        int pageIndex = 1;
        for(;;){
            String newUrl = CommonData.listArea.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getAreaData(HttpUtil.submitGet(newUrl));
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
                    AreaEntity areaEntity = new AreaEntity();
                    areaEntity.setCode(code);
                    areaEntity.setName(name);
                    areaEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    try {
                        areaDao.save(areaEntity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getAreaData(String respData){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(CommonData.areaPre);
            respData = respData.substring(preIndex+CommonData.areaPre.length()+1,respData.length()-2);
        }
        return respData;
    }
}
