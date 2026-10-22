package com.project.excel.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.excel.entity.Detail;

public interface DetailRepo extends JpaRepository<Detail, Long>{
    List<Detail> findByPoNumber(String poNumber);
    List<Detail> findByPoNumberIn(Collection<String> poNumbers);
}
