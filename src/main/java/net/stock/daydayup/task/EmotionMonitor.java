/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stock.daydayup.bean.LimitUpBean;
import net.stock.daydayup.bean.SpeculateEmotionBean;
import net.stock.daydayup.bean.StockValueEntity;
import net.stock.daydayup.constant.CommonData;
import net.stock.daydayup.service.EasyMoneyApiService;
import net.stock.daydayup.util.DFCFDataAnalyse;
import net.stock.daydayup.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 市场情绪监控
 *
 * @author:dailm
 * @create at :2022/9/29 15:38
 */
@Component
public class EmotionMonitor {

    @Autowired
    private EasyMoneyApiService easyMoneyApiService;

    /**
     * 投机情绪
     */
    public SpeculateEmotionBean getSpeculateEmotion(){
        return new SpeculateEmotionBean();
    }

    public Date string2Date(String str,String format){
        java.text.SimpleDateFormat formatter = new SimpleDateFormat( format);
        Date date = null;
        try {
            date = new Date(formatter.parse(str).getTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SpeculateEmotionBean getMarketEmotion(){
        //抓取所有股票当前行情
        List<StockValueEntity> stockList = easyMoneyApiService.getAll();
        String day = getLocalDate2String("yyyyMMdd");
        //从涨跌版获取信息
        List<LimitUpBean> limitUpBeans = getLimitUp("20220930");
        //投机情绪
        SpeculateEmotionBean speculateEmotionBean = getSpeculateEmotionBean(stockList,limitUpBeans);
        //
        return speculateEmotionBean;
    }


    /**
     * 投机情绪
     * @param stockList
     * @param limitUpBeans
     * @return
     */
    public SpeculateEmotionBean getSpeculateEmotionBean(List<StockValueEntity> stockList,List<LimitUpBean> limitUpBeans) {

        //过滤剩下当日涨停过
        List<StockValueEntity> hadLimitUpList = stockList.stream().filter(item ->  isCarefor(item)).collect(Collectors.toList());
        //当前涨停数量,包含新股
        List<StockValueEntity> nowLimitUpListed = hadLimitUpList.stream().filter(item ->item.getPrice().compareTo(item.getHeight())>=0).collect(Collectors.toList());
        //当天炸板
        List<StockValueEntity> loseLimitUpList = hadLimitUpList.stream().filter(item->item.getPrice().compareTo(item.getHeight())==-1).collect(Collectors.toList());
        SpeculateEmotionBean speculateEmotionBean = new SpeculateEmotionBean();
        //炸板数量
        int lose = loseLimitUpList.size();
        //当前封板数量
        int now = nowLimitUpListed.size();
        speculateEmotionBean.setLimitUpCount(hadLimitUpList.size());
        //炸板率
        Double loseRate = lose/Double.parseDouble(hadLimitUpList.size()+".00")*100.00;
        speculateEmotionBean.setLoseLimitUpRate(scale(loseRate,2));

        List<StockValueEntity> notIn = nowLimitUpListed.stream().filter(item -> notIn(item,limitUpBeans)).collect(Collectors.toList());
        List<LimitUpBean> notIn2 = limitUpBeans.stream().filter(item ->notIn2(item,nowLimitUpListed)).collect(Collectors.toList());
        limitUpBeans.sort(new Comparator<LimitUpBean>() {
            @Override
            public int compare(LimitUpBean o1, LimitUpBean o2) {
                int rs = o1.getLbc().compareTo(o2.getLbc());
                if (rs==0){
                    rs = o1.getCt().compareTo(o2.getCt());
                }
                //连板数量和N天内涨停数量相同情况下，看所用天数谁少
                if(rs==0){
                    rs = o2.getDays().compareTo(o1.getDays());
                }
                return rs;
            }
        });
        speculateEmotionBean.setContinueLimitUpCount(limitUpBeans.size());
        speculateEmotionBean.setContinueLimitUpHeight(limitUpBeans.get(limitUpBeans.size()-1).getLbc());
        speculateEmotionBean.setName(limitUpBeans.get(limitUpBeans.size()-1).getName());
        speculateEmotionBean.setHy(limitUpBeans.get(limitUpBeans.size()-1).getHybk());
        return speculateEmotionBean;
    }

    public boolean notIn2(LimitUpBean limitUpBean,List<StockValueEntity> stockValueEntityList){
        boolean isIn = false;
        for(StockValueEntity stockValueEntity : stockValueEntityList){
            if(limitUpBean.getCode().equals(stockValueEntity.getStockcode())){
                isIn = true;
                break;
            }
        }
        return !isIn;
    }

    public boolean notIn(StockValueEntity item,List<LimitUpBean> limitUpBeans){
        boolean isIn = false;
        for(LimitUpBean limitUpBean : limitUpBeans){
            if(limitUpBean.getCode().equals(item.getStockcode())){
                isIn = true;
                break;
            }
        }
        return !isIn;
    }

    public static Double getLimitUpPrice(String code,Double yesClose){
        if(code.startsWith("3")||code.startsWith("68")){
            yesClose = yesClose * 1.2;
        }else{
            yesClose = yesClose * 1.1;
        }
        return scale(yesClose,2);
    }

    public static Double scale(Double price,int bit){
        //保留两位，四舍五入
        BigDecimal bg = new BigDecimal(price);
        double newValue = bg.setScale(bit, RoundingMode.HALF_UP).doubleValue();
        return newValue;
    }

    public static Double getLimitDownPrice(String code,Double yesClose){
        if(code.startsWith("3")||code.startsWith("68")){
            yesClose = yesClose * 0.8;
        }else{
            yesClose = yesClose * 0.9;
        }
        return scale(yesClose,2);
    }


    public boolean isCarefor(StockValueEntity stockValueEntity){
        //沪深A，创业板
        boolean isCarefor =  stockValueEntity.getStockcode().startsWith("68")||stockValueEntity.getStockcode().startsWith("60")||stockValueEntity.getStockcode().startsWith("0")||stockValueEntity.getStockcode().startsWith("3");
        if(isCarefor){
            //未开盘直接过滤
            if(stockValueEntity.getPrice().compareTo(Double.valueOf(0.0))==0)
            {
                return false;
            }
            Double limitUpPrice = getLimitUpPrice(stockValueEntity.getStockcode(),stockValueEntity.getYesClose());
            //最高价等于涨停价，超过的是新股
            return stockValueEntity.getHeight().compareTo(limitUpPrice)>=0;
        }
        return false;
    }

    public List<LimitUpBean> getLimitUp(String day){
        String myUrl = CommonData.zttjUrl.replace("${pageIndex}","0").replace("${pageSize}","8000")
                .replace("${date}",day);
        String resp = HttpUtil.submitGet(myUrl,10000,15);
        String respData = DFCFDataAnalyse.getStockData(resp,CommonData.zttjPre);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode respJson = objectMapper.readValue(respData, JsonNode.class);
            if(respJson!=null&&!respJson.isEmpty()){
                Iterator<JsonNode> iterator = respJson.get("data").get("pool").iterator();
                List<LimitUpBean> limitUpBeans = new ArrayList<>();
                while (iterator.hasNext()){
                    JsonNode item = iterator.next();
                    LimitUpBean limitUpBean = new LimitUpBean();
                    limitUpBean.setCode(item.get("c").asText());
                    limitUpBean.setMarket(item.get("m").asInt());
                    limitUpBean.setName(item.get("n").asText());
                    limitUpBean.setPrice(item.get("p").asInt()/1000.0);
                    limitUpBean.setZdp(item.get("zdp").asDouble());
                    limitUpBean.setAmount(item.get("amount").asLong()*1.0);
                    limitUpBean.setLtsz(item.get("ltsz").asDouble());
                    limitUpBean.setTshare(item.get("tshare").asDouble());
                    limitUpBean.setHs(item.get("hs").asDouble());
                    limitUpBean.setLbc(item.get("lbc").asInt());
                    limitUpBean.setFirstTime(string2Date(day+" "+getTime(item.get("fbt").asInt()),"yyyyMMdd HH:ss:mm"));
                    limitUpBean.setLastTime(string2Date(day+" "+getTime(item.get("lbt").asInt()),"yyyyMMdd HH:ss:mm"));
                    limitUpBean.setFund(item.get("fund").asLong()*1.0);
                    limitUpBean.setZbc(item.get("zbc").asInt());
                    limitUpBean.setHybk(item.get("hybk").asText());
                    limitUpBean.setDays(item.get("zttj").get("days").asInt());
                    limitUpBean.setCt(item.get("zttj").get("ct").asInt());
                    limitUpBeans.add(limitUpBean);
                }
                return limitUpBeans;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLocalDate2String(String format) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        String dateStr = date.format(fmt);
        return dateStr;
    }



    public static String getTime(Integer time){
        String strTime = time+"";
        if (strTime.length()==5){
            strTime="0"+strTime;
        }
        String finalTime = "";
        for(int i=0;i<6;i++){
            finalTime += strTime.charAt(i);
            if(i%2==1&&(i+1)!=6){
                finalTime += ":";
            }
        }
        return finalTime;
    }

    public static void main(String[] args){
        System.out.println(getLimitUpPrice("000411",10.45));
        System.out.println(getLimitUpPrice("002901",36.54));
        System.out.println(getLimitDownPrice("002641",7.17));
        System.out.println(getLimitDownPrice("603089",9.31));

        EmotionMonitor emotionMonitor = new EmotionMonitor();
        emotionMonitor.getLimitUp("20220930");
    }
}
