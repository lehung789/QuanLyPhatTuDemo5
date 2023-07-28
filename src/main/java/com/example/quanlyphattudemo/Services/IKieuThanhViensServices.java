package com.example.quanlyphattudemo.Services;

import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Models.KieuThanhViens;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKieuThanhViensServices {
    public String addNewKieuThanhViens(KieuThanhViens kieuThanhViensMoi);
    public String remakeKieuThanhViens(KieuThanhViens kieuThanhViensSua);
    public KieuThanhViens removeKieuThanhViens(int kieuThanhViensId);
    public Page<KieuThanhViens> pageFilterKieuThanhViens(String code, String tenKieu, Pageable pageable);
}
