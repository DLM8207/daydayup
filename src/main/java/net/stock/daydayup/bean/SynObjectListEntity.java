/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/9/1 14:18
 */
@Data
@Entity
@Table(name = "syn_object_list")
public class SynObjectListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;
    private String code;
    private String name;
    private Timestamp createAt;
}
