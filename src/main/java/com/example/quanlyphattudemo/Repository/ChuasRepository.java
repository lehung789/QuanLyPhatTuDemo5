package com.example.quanlyphattudemo.Repository;

import com.example.quanlyphattudemo.Models.Chuas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChuasRepository extends JpaRepository<Chuas,Integer> {
    @Query(value = "SELECT * FROM chuas WHERE (?1 IS NULL OR tenchua = ?1 ) AND (?2 IS NULL OR ngaythanhlap = ?2 ) AND (?3 IS NULL OR diachi = ?3 )",nativeQuery = true)
    List<Chuas> findFilter(String tenChua, String ngayThanhLap, String diaChi);
}
