package com.example.quanlyphattudemo.Services;

import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Repository.ChuasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChuasServices implements IChuasServices {
    @Autowired
    private ChuasRepository chuasRepository;

    // them kieu thanh vien
    @Override
    public String addNewChua(Chuas chuasMoi) {
        chuasMoi.setCapNhap(LocalDate.now());
        chuasRepository.save(chuasMoi);
        return "Thêm thành công";
    }

    // sua chua
    @Override
    public String remakeChuas(Chuas chuasSua) {
        Optional<Chuas> chuas = chuasRepository.findById(chuasSua.getId());
        if (chuas.isEmpty())
            return "Không tìm thấy chùa";
        Chuas ch = chuas.get();
        ch.setTenChua(chuasSua.getTenChua());
        ch.setNgayThanhLap(chuasSua.getNgayThanhLap());
        ch.setDiaChi(chuasSua.getDiaChi());
        ch.setTruTri(chuasSua.getTruTri());
        ch.setCapNhap(LocalDate.now());
        chuasRepository.save(ch);
        return "Sửa thành công";
    }

    // xoa chua
    @Override
    public Chuas removeChuas(int chuaId) {
        Optional<Chuas> chuas = chuasRepository.findById(chuaId);
        if (chuas.isEmpty())
            return null;
        Chuas c = chuas.get();
        chuasRepository.delete(c);
        return c;
    }

    // loc va phan trang
    @Override
    public Page<Chuas> pageFilterChuas(String tenChua, String ngayThanhLap, String diaChi, Pageable pageable) {
        if (tenChua != null || ngayThanhLap != null || diaChi != null) {
            List<Chuas> chuas = chuasRepository.findFilter(tenChua, ngayThanhLap, diaChi);
            return new PageImpl<>(chuas, pageable, chuas.size());
        }
        return chuasRepository.findAll(pageable);
    }
}
