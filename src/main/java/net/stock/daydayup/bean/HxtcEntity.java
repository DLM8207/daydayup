/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author:dailm
 * @create at :2022/9/15 8:36
 */
@Data
@Entity
@Table(name = "hxtc")
public class HxtcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Date day;
    private String content;
    private Date createAt;
    private String keyword;
    private String keyClassif;
    private String keyClassifCode;
}
