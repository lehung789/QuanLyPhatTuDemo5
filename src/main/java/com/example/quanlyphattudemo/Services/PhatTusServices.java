package com.example.quanlyphattudemo.Services;

import com.example.quanlyphattudemo.Models.DaoTrangs;
import com.example.quanlyphattudemo.Models.DonDangKys;
import com.example.quanlyphattudemo.Models.PhatTus;
import com.example.quanlyphattudemo.Repository.DaoTrangsRepository;
import com.example.quanlyphattudemo.Repository.DonDangKysRepository;
import com.example.quanlyphattudemo.Repository.PhatTusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhatTusServices implements IPhatTusServices {
    @Autowired
    private PhatTusRepository phatTusRepository;
    @Autowired
    private DonDangKysRepository donDangKysRepository;

    @Autowired
    private DaoTrangsRepository daoTrangsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // thêm phật tử
    @Override
    public String addNewPhatTu(PhatTus ptM) {
        Optional<PhatTus> phatTus = phatTusRepository.findByEmail(ptM.getEmail());
        if (phatTus.isPresent()) {
            return "Email đã tồn tại";
        }
        String matKhau = ptM.getMatKhau();
        if (!matKhau.matches(".*\\d.*") || !matKhau.matches(".*[^\\w\\s]*."))
            return "Mật khẩu có ít nhất 1 số hoặc 1 kí tự";
        phatTusRepository.save(ptM);
        return "Đăng ký thành công!";

    }

    // sửa phật tử
    @Override
    public PhatTus remakePhatTu(PhatTus ptS) {
        Optional<PhatTus> phatTus = phatTusRepository.findById(ptS.getId());
        if (phatTus.isEmpty()) {
            return null;
        }
        PhatTus pt = phatTus.get();
        pt.setHo(ptS.getHo());
        pt.setTenDem(ptS.getTenDem());
        pt.setTen(ptS.getTen());
        pt.setPhapDanh(ptS.getPhapDanh());
        pt.setAnhChup(ptS.getAnhChup());
        pt.setSoDienThoai(ptS.getSoDienThoai());
        pt.setEmail(ptS.getEmail());
        //     pt.setMatKhau(ptS.getMatKhau());
        pt.setNgaySinh(ptS.getNgaySinh());
        pt.setNgayXuatGia(ptS.getNgayXuatGia());
        pt.setDaHoanTuc(ptS.getDaHoanTuc());
        pt.setNgayHoanTuc(ptS.getNgayHoanTuc());
        pt.setGioiTinh(ptS.getGioiTinh());
        pt.setNgayCapNhap(ptS.getNgayCapNhap());
        pt.setKieuThanhViens(ptS.getKieuThanhViens());
        pt.setChuas(ptS.getChuas());
        phatTusRepository.save(pt);
        return pt;
    }

    // tìm theo tên
    @Override
    public List<PhatTus> filterName(String name) {
        List<PhatTus> ptList = new ArrayList<>();
        for (PhatTus pt : phatTusRepository.findAll()) {
            if (pt.getTen().toLowerCase().contains(name.toLowerCase()))
                ptList.add(pt);
        }
        return ptList;
    }

    // tìm theo pháp danh
    @Override
    public List<PhatTus> filterPhapDanh(String phapDanh) {
        List<PhatTus> ptlist = new ArrayList<>();
        for (PhatTus pt : phatTusRepository.findAll()) {
            if (pt.getPhapDanh().toLowerCase().equals(phapDanh.toLowerCase()))
                ptlist.add(pt);
        }
        return ptlist;
    }

    // tìm theo giới tính
    @Override
    public List<PhatTus> filterGioiTinh(int gioiTinh) {
        List<PhatTus> phatTusList = new ArrayList<>();
        for (PhatTus pt : phatTusRepository.findAll()) {
            if (pt.getGioiTinh() == gioiTinh) {
                phatTusList.add(pt);
            }
        }
        return phatTusList;
    }


    // phân trang lọc dữ liệu theo tên, pháp danh, giới tính, trang thái
    @Override
    public Page<PhatTus> pagePhatTu(String name, String phapDanh, String gioiTinh, String trangThai, Pageable pageable) {
        if (name != null || phapDanh != null || gioiTinh != null || trangThai != null) {
            List<PhatTus> phatTusList = phatTusRepository.getPhatTu(name, phapDanh, gioiTinh, trangThai);
            return new PageImpl<>(phatTusList, pageable, phatTusList.size());
        }
        return phatTusRepository.findAll(pageable);
    }

    public boolean testPassword(String mk) {
        if (!mk.matches(".*\\d.*") || !mk.matches(".*[^\\w\\s]*."))
            return true;
        return false;
    }

    // tạo chức năng đổi mật khẩu
    @Override
    public String doiMatKhau(String email, String matKhau, String newPassword) {
        Optional<PhatTus> phatTus = phatTusRepository.findByEmail(email);
        if (phatTus.isPresent()) {
            PhatTus phatTus1 = phatTus.get();
            if (passwordEncoder.matches(matKhau, phatTus1.getMatKhau())) {
                if (passwordEncoder.matches(newPassword, phatTus1.getMatKhau())) {
                    return "Trùng mật khẩu cũ";
                } else {
                    if (testPassword(newPassword))
                        return "Mật khẩu có ít nhất 1 số hoặc 1 kí tự";
                    phatTus1.setMatKhau(passwordEncoder.encode(newPassword));
                    phatTusRepository.save(phatTus1);
                    return "Đổi mật khẩu thành công.";
                }
            } else {
                return "Mật khẩu hiện tại không đúng.";
            }
        } else {
            return "Tài khoản không tồn tại";
        }
    }

    // tạo đơn đăng kí
    @Override
    public String addNewDonDangKi(String ten, String phapDanh, String email, String sdt, int daoTrangId) {
        DonDangKys donDangKys = new DonDangKys();
        Optional<PhatTus> phatTus = phatTusRepository.findByTPES(ten, phapDanh, email, sdt);
        Optional<DaoTrangs> daoTrangs = daoTrangsRepository.findById(daoTrangId);
        if (phatTus.isEmpty()) {
            return "Không tìm thấy thông tin phật tử";
        } else {
            Optional<DonDangKys> existingDon = donDangKysRepository.findByPhatTus(phatTus.get());
            if (existingDon.isPresent()) {
                return "Đơn đăng ký đã tồn tại";
            } else {
                donDangKys.setPhatTus(phatTus.get());
                donDangKys.setTrangThaiDon(0);
                donDangKys.setDaoTrangs(daoTrangs.get());
                donDangKys.setNgayGuiDon(LocalDate.now());
                donDangKysRepository.save(donDangKys);
                return "Đăng kí thành công.";
            }
        }
    }

    // xủ lý đơn đăng kí
    public String xuLyDon(int donDangKiId, int trangThai, int nguoiXuLy) {
        Optional<DonDangKys> donDangKys = donDangKysRepository.findById(donDangKiId);
        if (donDangKys.isEmpty())
            return "Đơn đăng kí không tồn tại";
        else {
            if (donDangKys.get().getTrangThaiDon() == 0 && trangThai == 0)
                return "Không duyệt";
            DonDangKys ddki = donDangKys.get();
            DaoTrangs daoTrangs = ddki.getDaoTrangs();
            ddki.setDaoTrangs(donDangKys.get().getDaoTrangs());
            daoTrangs.setSoThanhVienThamGia(daoTrangs.getSoThanhVienThamGia() + 1);
            ddki.setNguoiXuLyId(nguoiXuLy);
            ddki.setTrangThaiDon(trangThai);
            ddki.setNgayXuLy(LocalDate.now());
            donDangKysRepository.save(ddki);
            return "Duyệt thành công";
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return phatTusRepository.existsByEmail(email);
    }

    @Override
    public boolean existsBySoDienThoai(String soDienThoai) {
        return phatTusRepository.existsBySoDienThoai(soDienThoai);
    }

    @Override
    public Optional<PhatTus> findByEmail(String email) {
        return phatTusRepository.findByEmail(email);
    }

    @Override
    public PhatTus save(PhatTus user) {
        return phatTusRepository.save(user);
    }

}

