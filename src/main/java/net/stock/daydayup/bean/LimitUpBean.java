/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import java.sql.Date;

/**
 * {
 *         "c": "001255", code
 *         "m": 0, 市场  0深市 1沪市
 *         "n": "N博菲",
 *         "p": 28470,最新价格，需除以1000
 *         "zdp": 44.00606918334961, 涨跌幅
 *         "amount": 35844742,成交额
 *         "ltsz": 569400000.0,流通市值
 *         "tshare": 2277600000.0,总市值
 *         "hs": 6.334259986877441,换手率
 *         "lbc": 1,连扳次数
 *         "fbt": 100000,首次封板时间 10：00：00
 *         "lbt": 100000,最后封板时间 10：00：00
 *         "fund": 257218165,封板资金
 *         "zbc": 0,炸板次数
 *         "hybk": "化学原料",所属行业
 *         "zttj": {
 *           "days": 1,N天
 *           "ct": 1，N天中涨停次数
 *         }
 *       }
 * @author:dailm
 * @create at :2022/10/1 16:23
 */
@Data
public class LimitUpBean {
    private String code;
    private Integer market;
    private String name;
    private Double price;
    private Double zdp;
    private Double amount;
    private Double ltsz;
    private Double tshare;
    private Double hs;
    private Integer lbc;
    private Date firstTime;
    private Date lastTime;
    private Double fund;
    private Integer zbc;
    private String hybk;
    private Integer days;
    private Integer ct;
}
