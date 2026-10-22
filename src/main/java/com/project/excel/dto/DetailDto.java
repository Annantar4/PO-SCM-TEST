package com.project.excel.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class DetailDto {
    
    @JacksonXmlProperty(localName = "PO_NUMBER")
    private String poNumber;

    @JacksonXmlProperty(localName = "PART_NO")
    private String partNo;

    @JacksonXmlProperty(localName = "PART_NAME")
    private String partName;

    @JacksonXmlProperty(localName = "QTY")
    private Integer quantity;

    @JacksonXmlProperty(localName = "UNIT")
    private String unit;

    @JacksonXmlProperty(localName = "PRICE")
    private Integer price;

}
