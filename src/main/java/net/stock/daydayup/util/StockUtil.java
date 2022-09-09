/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.util;

import net.stock.daydayup.bean.StockLowHeightEntity;
import net.stock.daydayup.bean.StockValueEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:dailm
 * @create at :2022/9/8 9:54
 */
@Repository
public class StockUtil {

    /**
     * stockValueEntityList 按日期排序的值
     * 1.低价在高价左侧,判断高价右侧的价格是否跌破10%,跌破则从高价右侧从新找阶段低价，和阶段高价
     * 2.低价在高价右侧，则在低价寻找新阶段高价
     */
    public static void getStockLowHeightInfo(List<StockValueEntity> stockValueEntityList, StockLowHeightEntity stockLowHeight){
        //低价在高价左侧_-``
        if(stockLowHeight.getHeightDay().after(stockLowHeight.getLowDay())){
            //高价是最后一天,从后往前找新低，
            if(stockLowHeight.getHeightDay().equals(stockValueEntityList.get(stockValueEntityList.size()-1).getDay())){
                

            }else {
                //高价在中间
                //高价右侧的股价列表
                List<StockValueEntity> newList = stockValueEntityList.stream().filter(item -> !item.getDay().before(stockLowHeight.getHeightDay())).collect(Collectors.toList());
                //按最低价排序
                Collections.sort(newList, new Comparator<StockValueEntity>() {
                    @Override
                    public int compare(StockValueEntity o1, StockValueEntity o2) {
                        return o1.getLower().compareTo(o2.getLower());
                    }
                });
                //最低值
                StockValueEntity lowItem = newList.get(0);
                //按最高价排序
                Collections.sort(newList, new Comparator<StockValueEntity>() {
                    @Override
                    public int compare(StockValueEntity o1, StockValueEntity o2) {
                        return o1.getHeight().compareTo(o2.getHeight());
                    }
                });
                //最高值
                StockValueEntity heightItem = newList.get(newList.size() - 1);
            }

        }
        //低价在高价右侧``-_
        else{

        }
    }
}
