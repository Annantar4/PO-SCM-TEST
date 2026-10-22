package com.project.excel.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@JacksonXmlRootElement(localName = "HEADERS")
public class HeadersWrapper {
    
    @JacksonXmlProperty(localName = "HEADER")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<HeaderDto> headers; 

}
