package com.project.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.excel.entity.Header;

public interface HeaderRepo extends JpaRepository<Header, Long>{
    
}
