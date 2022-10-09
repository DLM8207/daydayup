/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

/**
 * 投机情绪
 *
 * @author:dailm
 * @create at :2022/9/29 15:45
 */
@Data
public class SpeculateEmotionBean {
    //涨停数量
    private Integer limitUpCount;
    //连扳数量
    private Integer continueLimitUpCount;
    //连扳高度
    private Integer continueLimitUpHeight;
    //炸板率
    private Double loseLimitUpRate;

    private String name;

    private String hy;

    @Override
    public String toString() {
        return "投机情绪{" +
                "涨停过数量:" + limitUpCount +
                ", 连板数量:" + continueLimitUpCount +
                ", 连扳高度:" + continueLimitUpHeight +
                ", 炸板率:" + loseLimitUpRate +
                ", 代表:" + name +
                ", 行业:" + hy +
                '}';
    }
}
