/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.HxtcEntity;
import net.stock.daydayup.bean.ObjectEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.repository.HxtcRepository;
import net.stock.daydayup.repository.ObjectEntityRepository;
import net.stock.daydayup.service.HxtcService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:dailm
 * @create at :2022/9/15 9:37
 */
@Service
public class HxtcServiceImpl implements HxtcService {

    @Autowired
    private ObjectEntityRepository objectEntityRepository;
    @Autowired
    private HxtcRepository hxtcRepository;

    private String getCode(String code){
        if(code.startsWith("8")||code.startsWith("4")){
            return "BJ"+code;
        }else if(code.startsWith("0")||code.startsWith("3")){
            return "SZ"+code;
        }else{
            return "SH"+code;
        }
    }

    @Override
    public void updateHxtc(String date, String hxtcCode,String keyword) {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(20,200,30, TimeUnit.HOURS, new ArrayBlockingQueue<>(50));
        long count = objectEntityRepository.count();
        int pageSize = 100;
        long pageNum = count / pageSize;
        if(count%pageSize!=0){
            pageNum++;
        }
        List<Integer> finsishIndex = new ArrayList<>();
        for(int i=0;i<pageNum;i++) {
            final int index = i;
            tpe.execute(() -> {
                Pageable pageable = PageRequest.of((index), pageSize);
                Page<ObjectEntity> pages = objectEntityRepository.findAll(pageable);
                if (!pages.isEmpty()) {
                    Iterator<ObjectEntity> iterator = pages.iterator();
                    while (iterator.hasNext()) {
                        ObjectEntity objectEntity = iterator.next();
                        String code = getCode(objectEntity.getCode());
                        String url = CommonData.stockTopic.replace("${code}", code);
                        String respData = HttpUtil.submitGet(url, 15000, 10);
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            if (respData == null || "".equals(respData))
                                continue;
                            JsonNode respJsonData = objectMapper.readValue(respData, JsonNode.class);
                            if (!respJsonData.isNull()) {
                                JsonNode hxtc = respJsonData.get("hxtc");
                                if (!hxtc.isNull()) {
                                    Iterator<JsonNode> hxtcIterator = hxtc.iterator();
                                    while (hxtcIterator.hasNext()) {
                                        JsonNode hxtcItem = hxtcIterator.next();
                                        String keyClassifCode = hxtcItem.get("KEY_CLASSIF_CODE").asText();
                                        String keyClassif = hxtcItem.get("KEY_CLASSIF").asText();
                                        String thisKeyword = hxtcItem.get("KEYWORD").asText();
                                        String content = hxtcItem.get("MAINPOINT_CONTENT").asText();
                                        if(hxtcCode!=null&&!"".equals(hxtcCode)) {
                                            if (hxtcCode.equals(keyClassifCode)) {
                                                HxtcEntity hxtcEntity = new HxtcEntity();
                                                hxtcEntity.setCode(objectEntity.getCode());
                                                hxtcEntity.setContent(content);
                                                hxtcEntity.setDay(getDay(content));
                                                hxtcEntity.setKeyword(thisKeyword);
                                                hxtcEntity.setKeyClassif(keyClassif);
                                                hxtcEntity.setKeyClassifCode(keyClassifCode);
                                                hxtcEntity.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
                                                hxtcRepository.save(hxtcEntity);
                                            }
                                        }
                                        //如果存在时间参数，则判断内容是否有时间，且时间不小于时间参数
                                        if(date!=null&&!"".equals(date)){
                                            Pattern p = Pattern.compile(CommonData.dateRegex);
                                            Matcher m = p.matcher(content);
                                            DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Date dateParam = sdf.parse(date);
                                            try {
                                                while (m.find()) {
                                                    String strDate = m.group();
                                                    Date thisDate = formatter.parse(strDate);
                                                    if(!thisDate.before(dateParam)) {
                                                        java.sql.Date day = new java.sql.Date(thisDate.getTime());
                                                        HxtcEntity hxtcEntity = new HxtcEntity();
                                                        hxtcEntity.setCode(objectEntity.getCode());
                                                        hxtcEntity.setContent(content);
                                                        hxtcEntity.setDay(day);
                                                        hxtcEntity.setKeyword(thisKeyword);
                                                        hxtcEntity.setKeyClassif(keyClassif);
                                                        hxtcEntity.setKeyClassifCode(keyClassifCode);
                                                        hxtcEntity.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
                                                        hxtcRepository.save(hxtcEntity);
                                                    }
                                                }
                                            }catch (Exception ee){
                                                ee.printStackTrace();
                                            }
                                        }
                                        if(keyword!=null&&!"".equals(keyword)){
                                            if(content.indexOf(keyword)!=-1){
                                                HxtcEntity hxtcEntity = new HxtcEntity();
                                                hxtcEntity.setCode(objectEntity.getCode());
                                                hxtcEntity.setContent(content);
                                                hxtcEntity.setDay(getDay(content));
                                                hxtcEntity.setKeyword(thisKeyword);
                                                hxtcEntity.setKeyClassif(keyClassif);
                                                hxtcEntity.setKeyClassifCode(keyClassifCode);
                                                hxtcEntity.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
                                                hxtcRepository.save(hxtcEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            finsishIndex.add(index);
            Collections.sort(finsishIndex);
            finsishIndex.forEach((item)->{System.out.print(item+" ");});
        }
    }

    public java.sql.Date getDay(String content){
        Pattern p = Pattern.compile(CommonData.dateRegex);
        Matcher m = p.matcher(content);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            while (m.find()) {
                String strDate = m.group();
                Date thisDate = formatter.parse(strDate);
                return new java.sql.Date(thisDate.getTime());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
