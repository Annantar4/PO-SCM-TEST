package com.project.excel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.project.excel.dto.DetailDto;
import com.project.excel.dto.HeaderDto;
import com.project.excel.dto.HeadersWrapper;
import com.project.excel.entity.Detail;
import com.project.excel.entity.Header;
import com.project.excel.repository.DetailRepo;
import com.project.excel.repository.HeaderRepo;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class XmlService {
    private final HeaderRepo headerRepo;
    private final DetailRepo detailRepo;

    public Map<String, String> generatePoXml(String targetFolderPath, String fileName) {
        try {
                // 1. Query Header
                List<Header> headers = headerRepo.findAll();

                List<String> poNumbers = headers.stream()
                        .map(Header::getPoNumber)
                        .toList();

                // 2. Query Detail (Batching via findByPoNumberIn)
                Map<String, List<Detail>> detailMap = detailRepo.findByPoNumberIn(poNumbers)
                        .stream()
                        .collect(Collectors.groupingBy(Detail::getPoNumber));

                // 3. Mapping Entity ke DTO pakai Lombok Builder / Stream
                List<HeaderDto> headerDtoList = headers.stream().map(h -> {
                List<Detail> details = detailMap.getOrDefault(h.getPoNumber(), List.of());

                List<DetailDto> detailDtoList = details.stream()
                        .map(d -> DetailDto.builder()
                                .poNumber(d.getPoNumber())
                                .partNo(d.getPartNo())
                                .partName(d.getPartName())
                                .quantity(d.getQuantity())
                                .unit(d.getUnit())
                                .price(d.getPrice())
                                .build())
                        .toList();

                return HeaderDto.builder()
                        .poNumber(h.getPoNumber())
                        .poDate(h.getPoDate() != null ? h.getPoDate().toString() : "")
                        .buyerName(h.getBuyerName())
                        .buyerAddr(h.getBuyerAddr())
                        .details(new HeaderDto.DetailsWrapper(detailDtoList))
                        .build();
                }).toList();

                // 4. Convert ke XML
                HeadersWrapper root = new HeadersWrapper(headerDtoList);

                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

                String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" 
                        + xmlMapper.writeValueAsString(root);

                Path folderPath = Paths.get(targetFolderPath);
                
                // Buat folder otomatis jika belum ada
                if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
                }

                Path filePath = folderPath.resolve(fileName);
                Files.writeString(filePath, xmlContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                // return filePath.toAbsolutePath().toString();

                return Map.of("status", "success");
        } catch (IOException e) {
                return Map.of("status", "failed", "message", e.getMessage());
        }
        

        // return xmlString.getBytes("UTF-8");
    }
}
