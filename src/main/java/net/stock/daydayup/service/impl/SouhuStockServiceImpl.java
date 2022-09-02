/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.ObjectEntity;
import net.stock.daydayup.bean.StockValueEntity;
import net.stock.daydayup.bean.SynObjectListEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.repository.ObjectEntityRepository;
import net.stock.daydayup.repository.StockValueRepository;
import net.stock.daydayup.repository.SynObjectListRepository;
import net.stock.daydayup.service.ObjectService;
import net.stock.daydayup.service.SouhuStockService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author:dailm
 * @create at :2022/8/30 8:49
 */
@Service
public class SouhuStockServiceImpl implements SouhuStockService {
    @Autowired
    SynObjectListRepository synObjectListRepository;
    @Autowired
    StockValueRepository stockValueRepository;

    @Override
    public void downloadHistoryData(int year) {
        long count = synObjectListRepository.count();
        int pageSize = 200;
        long pageNum = count / pageSize;
        if(count%pageSize!=0){
            pageNum++;
        }
        for(int i=0;i<pageNum;i++) {
            Pageable pageable = PageRequest.of((i + 1), pageSize);
            Page<SynObjectListEntity> pages = synObjectListRepository.findAll(pageable);
            if (!pages.isEmpty()) {
                Iterator<SynObjectListEntity> iterator = pages.iterator();
                while (iterator.hasNext()) {
                    SynObjectListEntity objectEntity = iterator.next();
                    String code = objectEntity.getCode();
                    String respData = getSohuData(code,year);
                    List<String> finishDays = stockValueRepository.findDayByCodeAndYear(code,year+"-01-01");
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        if(respData==null||"".equals(respData))
                            continue;
                        JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                        JsonNode dateValues = jsonNode.get("hq");
                        if(dateValues==null)
                            continue;
                        Iterator<JsonNode> iterator2 = dateValues.iterator();
                        List<StockValueEntity> savingList = new ArrayList<>();
                        while (iterator2.hasNext()){
                            //日期、开盘价、收盘价、涨跌额、涨跌幅、最低价、最高价、总手、总成交额、换手率
                            JsonNode node = iterator2.next();
                            String strDate = node.get(0).asText();
                            //当前股票，该日期值已经存在则跳过
                            if(finishDays!=null&&finishDays.indexOf(strDate)!=-1)
                                continue;
                            String strOpen = node.get(1).asText();
                            String strClose = node.get(2).asText();
                            String strChangeAmt = node.get(3).asText();
                            String strChangeRate = node.get(4).asText();
                            String strLow = node.get(5).asText();
                            String strHeight = node.get(6).asText();
                            String strCount = node.get(7).asText();
                            String strAmt = node.get(8).asText();
                            String strTNU = node.get(9).asText();
                            StockValueEntity stockValueEntity = new StockValueEntity();
                            stockValueEntity.setDay(Date.valueOf(strDate));
                            stockValueEntity.setClose(Double.valueOf(strClose));
                            stockValueEntity.setOpen(Double.valueOf(strOpen));
                            stockValueEntity.setHeight(Double.valueOf(strHeight));
                            stockValueEntity.setLower(Double.valueOf(strLow));
                            stockValueEntity.setVolume(Long.valueOf(strCount));
                            stockValueEntity.setTurnover(Double.valueOf(strAmt));
                            stockValueEntity.setAmtIncDec(Double.valueOf(strChangeAmt));
                            stockValueEntity.setIncDecRate(strChangeRate);
                            stockValueEntity.setTurnoverRate(strTNU);
                            stockValueEntity.setStockcode(code);
                            savingList.add(stockValueEntity);
                        }
                        if(savingList.size()>0){
                            stockValueRepository.saveAll(savingList);
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String getSohuData(String code,int year){
        String url = CommonData.sohuStockDataApi.replace("${code}","cn_"+code)
                .replace("${startDate}",year+"0101").replace("${endDate}",year+"1231");
        //15秒，重试10次
        String respData = HttpUtil.submitGet(url,15000,10);
        if(respData!=null&&!"".equals(respData)){
            String pre = "historySearchHandler(";
            int preIndex = respData.indexOf(pre);
            respData = respData.substring(preIndex+pre.length()+1,respData.length()-1);
            return respData;
        }
        return null;

    }
}
