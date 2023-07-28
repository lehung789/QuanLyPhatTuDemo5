package com.example.quanlyphattudemo.Repository;

import com.example.quanlyphattudemo.Models.KieuThanhViens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KieuThanhViensRepository extends JpaRepository<KieuThanhViens,Integer> {
    @Query(value = "SELECT * FROM kieuthanhviens WHERE ( ?1 IS NULL OR code =?1) AND (?2 IS NULL OR tenkieu =?2)", nativeQuery = true)

    List<KieuThanhViens> findFilter(String code, String tenKieu);
}
