package com.project.excel.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class HeaderDto {
    @JacksonXmlProperty(localName = "PO_NUMBER")
    private String poNumber;

    @JacksonXmlProperty(localName = "PO_DATE")
    private String poDate;

    @JacksonXmlProperty(localName = "BUYER_NAME")
    private String buyerName;

    @JacksonXmlProperty(localName = "BUYER_ADDR")
    private String buyerAddr;

    @JacksonXmlProperty(localName = "DETAILS")
    private DetailsWrapper details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailsWrapper {
        @JacksonXmlProperty(localName = "DETAIL")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<DetailDto> detailList;
    }
}
