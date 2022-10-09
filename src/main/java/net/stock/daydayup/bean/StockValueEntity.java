/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.UUID;

/**
 * @author:dailm
 * @create at :2022/8/30 16:24
 */
@Data
@Entity
@Table(name = "object_day_value")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class StockValueEntity {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private Date day;
    private String stockcode;
    private Double price;
    private Double open;
    private Double close;
    private Double height;
    private Double lower;
    private Long volume;
    private Double turnover;
    private Double amtIncDec;
    private String turnoverRate;
    private String incDecRate;
    private Double yesClose;
}
