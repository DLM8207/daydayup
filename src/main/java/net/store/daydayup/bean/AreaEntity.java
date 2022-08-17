/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:dailm
 * @create at :2022/8/17 17:54
 */
@Data
@Entity
@Table(name = "area")
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
}
