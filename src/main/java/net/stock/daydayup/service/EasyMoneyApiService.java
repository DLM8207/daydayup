/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.service;

import net.stock.daydayup.bean.StockValueEntity;

import java.util.List;

/**
 * @author:dailm
 * @create at :2022/9/1 15:52
 */
public interface EasyMoneyApiService {
    public void downloadTodayValue();

    public List<StockValueEntity> getAll();
}
