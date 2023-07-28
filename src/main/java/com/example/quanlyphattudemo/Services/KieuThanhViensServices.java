package com.example.quanlyphattudemo.Services;
import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Models.DaoTrangs;
import com.example.quanlyphattudemo.Models.KieuThanhViens;
import com.example.quanlyphattudemo.Repository.KieuThanhViensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KieuThanhViensServices implements IKieuThanhViensServices {
    @Autowired
    private KieuThanhViensRepository kieuThanhViensRepository;

    // them kieu thanh vien
    @Override
    public String addNewKieuThanhViens(KieuThanhViens kieuThanhViensMoi) {
        kieuThanhViensRepository.save(kieuThanhViensMoi);
        return "Thêm thành công";
    }

    @Override
    public String remakeKieuThanhViens(KieuThanhViens kieuThanhViensSua) {
        Optional<KieuThanhViens> kieuThanhViens = kieuThanhViensRepository.findById(kieuThanhViensSua.getId());
        if (kieuThanhViens.isEmpty())
            return "Không tìm thấy kiểu thành viên";
        KieuThanhViens kkv = kieuThanhViens.get();
        kkv.setCode(kieuThanhViensSua.getCode());
        kkv.setTenKieu(kieuThanhViensSua.getTenKieu());
        kieuThanhViensRepository.save(kkv);
        return "Sửa thành công";
    }

    @Override
    public KieuThanhViens removeKieuThanhViens(int kieuThanhViensId) {
        Optional<KieuThanhViens> kieuThanhViens = kieuThanhViensRepository.findById(kieuThanhViensId);
        if (kieuThanhViens.isEmpty())
            return null;
        KieuThanhViens ktv = kieuThanhViens.get();
        kieuThanhViensRepository.delete(ktv);
        return ktv;
    }

    @Override
    public Page<KieuThanhViens> pageFilterKieuThanhViens(String code, String tenKieu, Pageable pageable) {
        if (code != null || tenKieu != null) {
            List<KieuThanhViens> kieuThanhViens = kieuThanhViensRepository.findFilter(code, tenKieu);
            return new PageImpl<>(kieuThanhViens);
        }
        return kieuThanhViensRepository.findAll(pageable);
    }
}
