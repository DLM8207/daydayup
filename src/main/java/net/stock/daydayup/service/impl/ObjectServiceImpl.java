/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.*;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.dao.ObjectDao;
import net.stock.daydayup.repository.*;
import net.stock.daydayup.service.ObjectService;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:dailm
 * @create at :2022/8/2 17:39
 */
@Service
public class ObjectServiceImpl implements ObjectService {

    final static String pre="jQuery1124049501677394074295_1655813740688";
    @Autowired
    private ObjectDao objectDao;
    @Autowired
    private ObjectEntityRepository objectEntityRepository;
    @Autowired
    private AreaObjectRepository areaObjectRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ConceptRepository conceptRepository;
    @Autowired
    private ConceptObjectRepository conceptObjectRepository;
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private IndustryObjectRepository industryObjectRepository;
    @Autowired
    private StockHeightLowRepository stockHeightLowRepository;
    @Autowired
    private RecombineRepository recombineRepository;

    @Override
    public ObjectEntity save(ObjectEntity entity) {
        return objectDao.save(entity);
    }

    @Override
    public void refreshStockInfo() {
        downLoadAndSave();
    }

    @Override
    public void refreshStockTag() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(20,200,30, TimeUnit.HOURS, new ArrayBlockingQueue<>(50));
        long count = objectEntityRepository.count();
        int pageSize = 100;
        long pageNum = count / pageSize;
        if(count%pageSize!=0){
            pageNum++;
        }
        List<Integer> finsishIndex = new ArrayList<>();
        for(int i=0;i<pageNum;i++){
            final int index = i;
            tpe.execute(()-> {
                Pageable pageable = PageRequest.of((index), pageSize);
                Page<ObjectEntity> pages = objectEntityRepository.findAll(pageable);
                if (!pages.isEmpty()) {
                    Iterator<ObjectEntity> iterator = pages.iterator();
                    while (iterator.hasNext()) {
                        ObjectEntity objectEntity = iterator.next();
                        String code = getCode(objectEntity.getCode());
                        String url = CommonData.stockTopic.replace("${code}", code);
                        String respData = HttpUtil.submitGet(url,15000,10);
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            if(respData==null||"".equals(respData))
                                continue;
                            JsonNode respJsonData = objectMapper.readValue(respData, JsonNode.class);
                            if (!respJsonData.isNull()) {
                                JsonNode ssbk = respJsonData.get("ssbk");
                                if (!ssbk.isNull()) {
                                    Iterator<JsonNode> ssbkIterator = ssbk.iterator();
                                    while (ssbkIterator.hasNext()) {
                                        JsonNode bk = ssbkIterator.next();
                                        JsonNode bkCode = bk.get("BOARD_CODE");
                                        String bkCodeStr = "";
                                        String bkNameStr = "";
                                        if (!bkCode.isNull()) {
                                            bkCodeStr = bkCode.asText();
                                        }
                                        JsonNode bkName = bk.get("BOARD_NAME");
                                        if (!bkName.isNull()) {
                                            bkNameStr = bkName.asText();
                                        }
                                        if (!"".equals(bkCodeStr)) {
                                        }
                                        if (!"".equals(bkNameStr)) {
                                            //地区板块
                                            updateArea(bkCodeStr, bkNameStr, objectEntity);
                                            //概念
                                            updateConcept(bkCodeStr, bkNameStr, objectEntity);
                                            //行业
                                            updateIndustry(bkCodeStr, bkNameStr, objectEntity);
                                        }
                                    }
                                }
                                JsonNode hxtc = respJsonData.get("hxtc");
                                if (!hxtc.isNull()) {
                                    Iterator<JsonNode> hxtcIterator = hxtc.iterator();
                                    while (hxtcIterator.hasNext()) {
                                        JsonNode hxtcItem = hxtcIterator.next();
                                        String KEY_CLASSIF_CODE = hxtcItem.get("KEY_CLASSIF_CODE").asText();
                                        String KEY_CLASSIF = hxtcItem.get("KEY_CLASSIF").asText();
                                        String content = hxtcItem.get("MAINPOINT_CONTENT").asText();
                                        if("006005".equals(KEY_CLASSIF_CODE)){
                                            updateConcept(KEY_CLASSIF_CODE,KEY_CLASSIF,objectEntity);
                                            updateZCCZ(content,objectEntity);
                                        }
                                    }
                                }
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                finsishIndex.add(index);
                Collections.sort(finsishIndex);
                finsishIndex.forEach((item)->{System.out.print(item+" ");});
            });
        }
    }

    @Override
    public void refreshLower() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(20,200,30, TimeUnit.HOURS, new ArrayBlockingQueue<>(50));
        long count = objectEntityRepository.count();
        int pageSize = 100;
        long pageNum = count / pageSize;
        if(count%pageSize!=0){
            pageNum++;
        }
        List<Integer> finsishIndex = new ArrayList<>();
        final Map<String,StockLowHeightEntity> stockLowHeightEntityMap = getStockHeightLowMap();
        for(int i=0;i<pageNum;i++) {
            final int index = i;
            tpe.execute(() -> {
                Pageable pageable = PageRequest.of((index), pageSize);
                Page<ObjectEntity> pages = objectEntityRepository.findAll(pageable);
                if (!pages.isEmpty()) {
                    Iterator<ObjectEntity> iterator = pages.iterator();
                    while (iterator.hasNext()) {
                        ObjectEntity objectEntity = iterator.next();
                        StockLowHeightEntity stockLowHeightEntity = stockLowHeightEntityMap.get(objectEntity.getCode());
                        if(stockLowHeightEntity==null){
                            continue;
                        }

                    }
                }
            });
        }
    }

    private void updateZCCZ(String content,ObjectEntity objectEntity){
        Pattern p = Pattern.compile(CommonData.dateRegex);
        Matcher m = p.matcher(content);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            while(m.find()) {
                String strDate = m.group();
                java.sql.Date day = new java.sql.Date(formatter.parse(strDate).getTime());
                RecombineEntity exit = recombineRepository.findByDayAndCode(day, objectEntity.getCode());
                if (exit != null) {
                    return;
                }
                RecombineEntity recombineEntity = new RecombineEntity();
                recombineEntity.setCode(objectEntity.getCode());
                recombineEntity.setContent(content);
                recombineEntity.setDay(day);
                recombineEntity.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
                recombineRepository.save(recombineEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //行业
    private void updateIndustry(String code,String name,ObjectEntity objectEntity){
        List<IndustryEntiry> industryEntiryList = industryRepository.findByName(name);
        if(industryEntiryList!=null&&industryEntiryList.size()>0){
            for(IndustryEntiry industryEntiry: industryEntiryList){
                IndustryObjectTagEntity industryObjectTagEntity = industryObjectRepository.findByIndustryAndObject(industryEntiry,objectEntity);
                if(industryObjectTagEntity==null){
                    IndustryObjectTagEntity industryTag = new IndustryObjectTagEntity();
                    industryTag.setIndustry(industryEntiry);
                    industryTag.setObject(objectEntity);
                    industryTag.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    industryObjectRepository.save(industryTag);
                }
            }
        }
    }

    //概念
    private void updateConcept(String code,String name,ObjectEntity objectEntity){
        List<ConceptEntity> conceptEntityList = conceptRepository.findByName(name);
        if(conceptEntityList!=null&&conceptEntityList.size()>0){
            for(ConceptEntity conceptEntity : conceptEntityList){
                ConceptObjectTagEntity conceptObjectTagEntity = conceptObjectRepository.findByConceptAndObject(conceptEntity,objectEntity);
                //不存在则保存
                if(conceptObjectTagEntity==null){
                    ConceptObjectTagEntity newConceptTag = new ConceptObjectTagEntity();
                    newConceptTag.setConcept(conceptEntity);
                    newConceptTag.setObject(objectEntity);
                    newConceptTag.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    conceptObjectRepository.save(newConceptTag);
                }
            }
        }
    }

    //地区
    private void updateArea(String code,String name,ObjectEntity objectEntity){
        List<AreaEntity> areaEntityList = areaRepository.findByName(name);
        if(areaEntityList!=null&&areaEntityList.size()>0){
            for(AreaEntity areaEntity : areaEntityList){
                AreaObjectTagEntigy areaObjectTagEntigy = areaObjectRepository.findByAreaAndObject(areaEntity,objectEntity);
                //不存在则保存
                if(areaObjectTagEntigy==null){
                    AreaObjectTagEntigy newAreaTag = new AreaObjectTagEntigy();
                    newAreaTag.setArea(areaEntity);
                    newAreaTag.setObject(objectEntity);
                    newAreaTag.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    areaObjectRepository.save(newAreaTag);
                }
            }
        }
    }

    private String getCode(String code){
        if(code.startsWith("8")||code.startsWith("4")){
            return "BJ"+code;
        }else if(code.startsWith("0")||code.startsWith("3")){
            return "SZ"+code;
        }else{
            return "SH"+code;
        }
    }

    private void downLoadAndSave(){
        int pageSize = 1000;
        int pageIndex = 1;
        List<String> listCode = objectEntityRepository.findAllCode();
        for(;;){
            String newUrl = CommonData.listStockUrl.replace("${pageNum}",pageIndex+"");
            newUrl = newUrl.replace("${pageSize}",pageSize+"");
            String respData = getStockData(HttpUtil.submitGet(newUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readValue(respData, JsonNode.class);
                JsonNode dataNode = jsonNode.get("data");
                if(dataNode==null||"null".equals(dataNode)||dataNode.isNull()){
                    return;
                }else{
                    pageIndex ++ ;
                }
                for(JsonNode node : dataNode.get("diff")){
                    String code = node.get("f12").asText();
                    String name = node.get("f14").asText();
                    if(listCode.indexOf(code)!=-1){
                        continue;
                    }
                    ObjectEntity stock = new ObjectEntity();
                    stock.setCode(code);
                    stock.setName(name);
                    stock.setCreateAt(new Timestamp(System.currentTimeMillis()));
                    try {
                        save(stock);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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

    /**
     *
     * @return
     */
    private Map<String,StockLowHeightEntity> getStockHeightLowMap(){
        Map<String,StockLowHeightEntity> stringStockLowHeightEntityMap = new HashMap<>();
        List<StockLowHeightEntity> stockLowHeightEntityList = stockHeightLowRepository.findAll();
        if(stockLowHeightEntityList!=null&&stockLowHeightEntityList.size()>0){
            stockLowHeightEntityList.forEach((entity)->{
                if(stringStockLowHeightEntityMap.get(entity.getStockcode())==null){
                    stringStockLowHeightEntityMap.put(entity.getStockcode(),entity);
                }
            });
        }
        return stringStockLowHeightEntityMap;
    }

    public static void main(String[] args) {
        String respData = "jQuery1124049501677394074295_1655813740688({\"rc\":0,\"rt\":6,\"svr\":182993924,\"lt\":1,\"full\":1,\"dlmkts\":\"\",\"data\":{\"total\":5069,\"diff\":[{\"f1\":2,\"f2\":\"-\",\"f3\":\"-\",\"f4\":\"-\",\"f5\":\"-\",\"f6\":\"-\",\"f7\":\"-\",\"f8\":\"-\",\"f9\":34.53,\"f10\":\"-\",\"f11\":\"-\",\"f12\":\"872925\",\"f13\":0,\"f14\":\"锦好医疗\",\"f15\":\"-\",\"f16\":\"-\",\"f17\":\"-\",\"f18\":15.66,\"f20\":771646500,\"f21\":265697504,\"f22\":\"-\",\"f23\":2.29,\"f24\":-2.43,\"f25\":-37.63,\"f62\":\"-\",\"f115\":34.75,\"f128\":\"-\",\"f140\":\"-\",\"f141\":\"-\",\"f136\":\"-\",\"f152\":2},{\"f1\":2,\"f2\":\"-\",\"f3\":\"-\",\"f4\":\"-\",\"f5\":\"-\",\"f6\":\"-\",\"f7\":\"-\",\"f8\":\"-\",\"f9\":22.41,\"f10\":\"-\",\"f11\":\"-\",\"f12\":\"871981\",\"f13\":0,\"f14\":\"晶赛科技\",\"f15\":\"-\",\"f16\":\"-\",\"f17\":\"-\",\"f18\":17.31,\"f20\":1323661080,\"f21\":365589277,\"f22\":\"-\",\"f23\":2.67,\"f24\":-2.09,\"f25\":-43.51,\"f62\":\"-\",\"f115\":19.11,\"f128\":\"-\",\"f140\":\"-\",\"f141\":\"-\",\"f136\":\"-\",\"f152\":2}]}});";
        System.out.println(getStockData(respData));
    }


}
