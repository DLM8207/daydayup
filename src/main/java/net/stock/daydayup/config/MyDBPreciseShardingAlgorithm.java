/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

/**
 * @author:dailm
 * @create at :2022/10/8 10:36
 */
public class MyDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        Date day = preciseShardingValue.getValue();
        Calendar cal=Calendar.getInstance();
        cal.setTime(day);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String monthStr = month < 10 ? "0" + month : month + "";
        for (String tableName : collection) {
            /**
             * 取模算法，分片健 % 表数量
             */
            return tableName+"_"+year+"_"+monthStr;
        }
        return null;
    }
}
