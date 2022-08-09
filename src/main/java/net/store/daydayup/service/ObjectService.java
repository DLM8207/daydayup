/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.service;

import net.store.daydayup.bean.ObjectEntity;

/**
 * @author:dailm
 * @create at :2022/8/2 17:33
 */
public interface ObjectService {
    public ObjectEntity save(ObjectEntity entity);

    public void refreshStockInfo();
}
