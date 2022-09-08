/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author:dailm
 * @create at :2022/9/8 8:48
 */
@Data
@Entity
@Table(name = "stock_height_low_view")
public class StockLowHeightEntity {
    @Id
    private String stockcode;
    private Date heightDay;
    private Double height;
    private Date lowDay;
    private Double lower;
}
