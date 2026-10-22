package com.project.excel.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.excel.service.ExcelService;
import com.project.excel.service.XmlService;

import lombok.RequiredArgsConstructor;


@RestController 
@RequiredArgsConstructor 
public class ExcelController {
    
    private final ExcelService excelService;

    private final XmlService xmlService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {

        return ResponseEntity.ok("hello world");
    }

    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> insertToDatabase() throws IOException{
        Map<String, String> response = excelService.insertToDatabase();

        if (("success".equals(response.get("status")))) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> getMethodName() throws IOException{
        // Tentukan folder dan nama file tujuan kamu
        String targetFolder = "C:/SCM/OUTBOX";
        String fileName = "generated_xml_from_database" + ".xml";

        Map<String, String> response = xmlService.generatePoXml(targetFolder, fileName);

        if (("success".equals(response.get("status")))) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        
    }
    
    
    

}
