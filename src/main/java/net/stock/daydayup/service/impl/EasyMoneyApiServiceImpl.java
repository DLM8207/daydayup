/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.ObjectEntity;
import net.stock.daydayup.bean.StockValueEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.repository.StockValueRepository;
import net.stock.daydayup.service.EasyMoneyApiService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:dailm
 * @create at :2022/9/1 16:06
 */
@Service
public class EasyMoneyApiServiceImpl implements EasyMoneyApiService {
    @Autowired
    private StockValueRepository stockValueRepository;

    @Override
    public void downloadTodayValue() {
        final int pageSize = 1000;
        int pageIndex = 1;
        for(;;){
            String newUrl = CommonData.listStockUrl.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getStockData(CommonData.stockPre,HttpUtil.submitGet(newUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                JsonNode dataNode = jsonNode.get("data");
                if(dataNode==null||"null".equals(dataNode)||dataNode.isNull()){
                    return;
                }else{
                    pageIndex ++ ;
                }
                /**
                 * f1:交易状态
                 * f2:现价
                 * f3:涨跌幅
                 * f4:涨跌额
                 * f5：交易量
                 * f6:交易额
                 * f7:振幅
                 * f8:换手率
                 * f9:市盈率（动）
                 * f10:量比
                 * f11:
                 * f12:Code
                 * f13:
                 * f14:Name
                 * f15:最高
                 * f16:最低
                 * f17:今开
                 * f18:昨收
                 * f20:总市值
                 * f21:流通市值
                 * f22:
                 * f23:市净率
                 * f24:
                 * f25:市盈率（TTM）
                 * f62:
                 */
                List<StockValueEntity> stockList = new ArrayList<>();
                for(JsonNode node : dataNode.get("diff")){
                    String code = node.get("f12").asText();
                    String name = node.get("f14").asText();
                    Double open = node.get("f17").asDouble();
                    Double close = node.get("f2").asDouble();
                    Double changeAmt = node.get("f4").asDouble();
                    String changeRate = node.get("f3").asDouble()+"%";
                    Double low = node.get("f16").asDouble();
                    Double height = node.get("f15").asDouble();
                    Long count = node.get("f5").asLong();
                    Double amt = node.get("f6").asDouble();
                    String tnu = node.get("f8").asDouble()+"%";
                    Double yesClose = node.get("f18").asDouble();
                    StockValueEntity stockValueEntity = new StockValueEntity();
                    stockValueEntity.setStockcode(code);
                    stockValueEntity.setDay(new Date(System.currentTimeMillis()));
                    stockValueEntity.setOpen(open);
                    stockValueEntity.setClose(close);
                    stockValueEntity.setPrice(close);
                    stockValueEntity.setAmtIncDec(changeAmt);
                    stockValueEntity.setIncDecRate(changeRate);
                    stockValueEntity.setLower(low);
                    stockValueEntity.setHeight(height);
                    stockValueEntity.setVolume(count);
                    stockValueEntity.setTurnover(amt);
                    stockValueEntity.setTurnoverRate(tnu);
                    stockValueEntity.setYesClose(yesClose);
                    stockList.add(stockValueEntity);
                }
                stockValueRepository.saveAll(stockList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<StockValueEntity> getAll() {
        final int pageSize = 1000;
        int pageIndex = 1;
        List<StockValueEntity> stockList = new ArrayList<>();
        for(;;){
            String newUrl = CommonData.listStockUrl.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getStockData(CommonData.stockPre,HttpUtil.submitGet(newUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                JsonNode dataNode = jsonNode.get("data");
                if(dataNode==null||"null".equals(dataNode)||dataNode.isNull()){
                    return stockList;
                }else{
                    pageIndex ++ ;
                }
                /**
                 * f1:交易状态
                 * f2:现价
                 * f3:涨跌幅
                 * f4:涨跌额
                 * f5：交易量
                 * f6:交易额
                 * f7:振幅
                 * f8:换手率
                 * f9:市盈率（动）
                 * f10:量比
                 * f11:
                 * f12:Code
                 * f13:
                 * f14:Name
                 * f15:最高
                 * f16:最低
                 * f17:今开
                 * f18:昨收
                 * f20:总市值
                 * f21:流通市值
                 * f22:
                 * f23:市净率
                 * f24:
                 * f25:市盈率（TTM）
                 * f62:
                 */
                for(JsonNode node : dataNode.get("diff")){
                    String code = node.get("f12").asText();
                    String name = node.get("f14").asText();
                    Double price = node.get("f2").asDouble();
                    Double open = node.get("f17").asDouble();
                    Double close = node.get("f2").asDouble();
                    Double changeAmt = node.get("f4").asDouble();
                    String changeRate = node.get("f3").asDouble()+"%";
                    Double low = node.get("f16").asDouble();
                    Double height = node.get("f15").asDouble();
                    Long count = node.get("f5").asLong();
                    Double amt = node.get("f6").asDouble();
                    String tnu = node.get("f8").asDouble()+"%";
                    Double yesClose = node.get("f18").asDouble();
                    StockValueEntity stockValueEntity = new StockValueEntity();
                    stockValueEntity.setStockcode(code);
                    stockValueEntity.setDay(new Date(System.currentTimeMillis()));
                    stockValueEntity.setOpen(open);
                    stockValueEntity.setClose(close);
                    stockValueEntity.setAmtIncDec(changeAmt);
                    stockValueEntity.setIncDecRate(changeRate);
                    stockValueEntity.setLower(low);
                    stockValueEntity.setHeight(height);
                    stockValueEntity.setVolume(count);
                    stockValueEntity.setTurnover(amt);
                    stockValueEntity.setTurnoverRate(tnu);
                    stockValueEntity.setYesClose(yesClose);
                    stockValueEntity.setPrice(price);
                    stockList.add(stockValueEntity);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStockData(String pre,String respData){
        if(respData!=null&&!"".equals(respData)){
            int preIndex = respData.indexOf(pre);
            respData = respData.substring(preIndex+pre.length()+1,respData.length()-2);
        }
        return respData;
    }
}
