/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.store.daydayup.bean.ObjectEntity;
import net.store.daydayup.constant.CommonData;
import net.store.daydayup.dao.ObjectDao;
import net.store.daydayup.service.ObjectService;
import net.store.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:dailm
 * @create at :2022/8/2 17:39
 */
@Service
public class ObjectServiceImpl implements ObjectService {

    final static String pre="jQuery1124049501677394074295_1655813740688";
    @Autowired
    private ObjectDao objectDao;

    @Override
    public ObjectEntity save(ObjectEntity entity) {
        return objectDao.save(entity);
    }

    @Override
    public void refreshStockInfo() {
        downLoadAndSave();
    }

    private void downLoadAndSave(){
        int pageSize = 1000;
        int pageIndex = 1;
        for(;;){
            String newUrl = CommonData.listStockUrl.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getStockData(HttpUtil.submitGet(newUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                JsonNode dataNode = jsonNode.get("data");
                if(dataNode==null||"null".equals(dataNode)){
                    return;
                }else{
                    pageIndex ++ ;
                }
                for(JsonNode node : dataNode.get("diff")){
                    String code = node.get("f12").asText();
                    String name = node.get("f14").asText();
                    ObjectEntity stock = new ObjectEntity();
                    stock.setCode(code);
                    stock.setName(name);
                    save(stock);
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStockData(String respData){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(pre);
            respData = respData.substring(preIndex+pre.length()+1,respData.length()-2);
        }
        return respData;
    }

    public static void main(String[] args) {
        String respData = "jQuery1124049501677394074295_1655813740688({\"rc\":0,\"rt\":6,\"svr\":182993924,\"lt\":1,\"full\":1,\"dlmkts\":\"\",\"data\":{\"total\":5069,\"diff\":[{\"f1\":2,\"f2\":\"-\",\"f3\":\"-\",\"f4\":\"-\",\"f5\":\"-\",\"f6\":\"-\",\"f7\":\"-\",\"f8\":\"-\",\"f9\":34.53,\"f10\":\"-\",\"f11\":\"-\",\"f12\":\"872925\",\"f13\":0,\"f14\":\"锦好医疗\",\"f15\":\"-\",\"f16\":\"-\",\"f17\":\"-\",\"f18\":15.66,\"f20\":771646500,\"f21\":265697504,\"f22\":\"-\",\"f23\":2.29,\"f24\":-2.43,\"f25\":-37.63,\"f62\":\"-\",\"f115\":34.75,\"f128\":\"-\",\"f140\":\"-\",\"f141\":\"-\",\"f136\":\"-\",\"f152\":2},{\"f1\":2,\"f2\":\"-\",\"f3\":\"-\",\"f4\":\"-\",\"f5\":\"-\",\"f6\":\"-\",\"f7\":\"-\",\"f8\":\"-\",\"f9\":22.41,\"f10\":\"-\",\"f11\":\"-\",\"f12\":\"871981\",\"f13\":0,\"f14\":\"晶赛科技\",\"f15\":\"-\",\"f16\":\"-\",\"f17\":\"-\",\"f18\":17.31,\"f20\":1323661080,\"f21\":365589277,\"f22\":\"-\",\"f23\":2.67,\"f24\":-2.09,\"f25\":-43.51,\"f62\":\"-\",\"f115\":19.11,\"f128\":\"-\",\"f140\":\"-\",\"f141\":\"-\",\"f136\":\"-\",\"f152\":2}]}});";
        System.out.println(getStockData(respData));
    }


}
