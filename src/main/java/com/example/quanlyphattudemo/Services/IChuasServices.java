package com.example.quanlyphattudemo.Services;

import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Models.DaoTrangs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IChuasServices {
    public String addNewChua(Chuas chuasMoi);
    public String remakeChuas(Chuas chuasSua);
    public Chuas removeChuas(int chuaId);
    public Page<Chuas> pageFilterChuas(String tenChua, String ngayThanhLap, String diaChi, Pageable pageable);
}
