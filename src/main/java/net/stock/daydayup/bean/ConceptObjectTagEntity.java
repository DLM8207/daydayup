/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author:dailm
 * @create at :2022/8/18 15:15
 */
@Data
@Entity
@Table(name = "concept_tag_object")
public class ConceptObjectTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "concept_id")
    private ConceptEntity concept;
    @ManyToOne
    @JoinColumn(name = "object_id")
    private ObjectEntity object;
    private Timestamp createAt;
}
