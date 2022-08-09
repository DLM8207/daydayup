/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author:dailm
 * @create at :2022/6/21 20:49
 */
@Data
@Entity
@Table(name = "object")
public class ObjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;
    private String code;
    private String name;
}
