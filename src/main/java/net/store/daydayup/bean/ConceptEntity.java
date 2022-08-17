/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:dailm
 * @create at :2022/8/17 15:45
 */
@Data
@Entity
@Table(name = "concept")
public class ConceptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String memo;
}
