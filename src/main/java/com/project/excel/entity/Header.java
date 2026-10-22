package com.project.excel.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table (name = "tm_header")
public class Header {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PO_NUMBER") 
    private String poNumber;

    @Column(name = "PO_DATE")
    private LocalDate poDate;

    @Column(name = "BUYER_NAME")
    private String buyerName;
    
    @Column(name = "BUYER_ADDR")
    private String buyerAddr;
}
