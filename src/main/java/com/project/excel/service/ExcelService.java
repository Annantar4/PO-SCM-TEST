package com.project.excel.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties.Json;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.project.excel.entity.Detail;
import com.project.excel.entity.Header;
import com.project.excel.repository.DetailRepo;
import com.project.excel.repository.HeaderRepo;

import lombok.RequiredArgsConstructor;

// import jakarta.annotation.Resource;

@Service 
@RequiredArgsConstructor 
public class ExcelService {
    
    // @Value("file:C:/SCM/INBOX/test-teknis.xlsx")
    // private Resource externalFolder;


    private final HeaderRepo headerRepo;
    private final DetailRepo detailRepo;

    public Map<String, String> insertToDatabase(){
        // List<List<String>> data = new ArrayList<>();
        try {
            Path path = Paths.get("C:/SCM/INBOX/test-teknis.xlsx");
            String header = insertHeader(path);
            String detail = insertDetail(path);
            return Map.of("status", "success");
        } catch (IOException e) {
            return Map.of("status", "failed", "message", e.getMessage());
        }
              
    }

    private String insertDetail(Path path) throws IOException{
        List<Detail> details = new ArrayList<>();
        try (InputStream iStream = Files.newInputStream(path);
            Workbook workbook = WorkbookFactory.create(iStream)){
                
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) {
                    continue;
                }
                // Header header = new Header();
                Detail detail = new Detail();
                DataFormatter formatter = new DataFormatter();

                String stringQty = formatter.formatCellValue(currentRow.getCell(3)).trim();
                Integer qty = Integer.parseInt(stringQty);

                // Cell cellPrice = currentRow.getCell(5);
                String stringPrice = formatter.formatCellValue(currentRow.getCell(5)).trim();
                Integer price = Integer.parseInt(stringPrice);

                detail.setPoNumber(formatter.formatCellValue(currentRow.getCell(0)));
                detail.setPartNo(formatter.formatCellValue(currentRow.getCell(1)));
                detail.setPartName(formatter.formatCellValue(currentRow.getCell(2)));
                detail.setQuantity(qty);
                detail.setUnit(formatter.formatCellValue(currentRow.getCell(4)));
                detail.setPrice(price);

                details.add(detail);
            }

            detailRepo.saveAll(details);
            // String json;
            return "SUCCESS";

        }
    } 

    private String insertHeader(Path path) throws IOException{
        List<Header> headers = new ArrayList<>();
        try (InputStream iStream = Files.newInputStream(path);
            Workbook workbook = WorkbookFactory.create(iStream)){
                
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) {
                    continue;
                }
                // Header header = new Header();
                Header header = new Header();
                DataFormatter formatter = new DataFormatter();
                Cell cellDate = currentRow.getCell(1);
                LocalDate date = cellDate.getLocalDateTimeCellValue().toLocalDate();

                header.setPoNumber(formatter.formatCellValue(currentRow.getCell(0)));
                header.setPoDate(date);
                header.setBuyerName(formatter.formatCellValue(currentRow.getCell(2)));
                header.setBuyerAddr(formatter.formatCellValue(currentRow.getCell(3)));

                headers.add(header);
            }

            headerRepo.saveAll(headers);
            // String json;
            return "SUCCESS"; 
        } 
    }
}
