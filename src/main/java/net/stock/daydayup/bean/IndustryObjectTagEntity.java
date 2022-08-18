/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/18 15:18
 */
@Data
@Entity
@Table(name = "industry_tag_object")
public class IndustryObjectTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "industry_id")
    private IndustryEntiry industry;
    @ManyToOne
    @JoinColumn(name = "object_id")
    private ObjectEntity object;
    private Timestamp createAt;
}
