/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/18 10:28
 */
@Data
@Entity
@Table(name="area_tag_object")
public class AreaObjectTagEntigy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;
    @ManyToOne
    @JoinColumn(name = "object_id")
    private ObjectEntity object;
    private Timestamp createAt;
}
