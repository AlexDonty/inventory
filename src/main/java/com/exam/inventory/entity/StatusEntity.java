package com.exam.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CAT_STATUS")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;
}
