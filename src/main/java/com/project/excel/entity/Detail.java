package com.project.excel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data 
@Table (name = "tm_detail")
public class Detail {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PO_NUMBER") 
    private String poNumber;
    
    @Column(name = "PART_NO") 
    private String partNo;

    @Column(name = "PART_NAME") 
    private String partName;

    @Column (name = "QTY")
    private Integer quantity;

    @Column(name = "UNIT") 
    private String unit;

    @Column (name = "PRICE")
    private Integer price;

}
