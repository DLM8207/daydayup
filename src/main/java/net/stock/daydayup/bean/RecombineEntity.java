/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author:dailm
 * @create at :2022/9/13 14:43
 */
@Data
@Table(name = "recombine_info")
@Entity
public class RecombineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Date day;
    private String content;
    private Date createAt;
}
