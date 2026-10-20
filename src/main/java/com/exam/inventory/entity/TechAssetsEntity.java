package com.exam.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TAB_TECHNOLOGICAL_ASSETS")
public class TechAssetsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "inventory_folio")
    private String folio;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @Column(name = "acquisition_cost")
    private BigDecimal acquisitionCost;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;



}
