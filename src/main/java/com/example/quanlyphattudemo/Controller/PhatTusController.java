package com.example.quanlyphattudemo.Controller;

import com.example.quanlyphattudemo.Models.PhatTus;
import com.example.quanlyphattudemo.Services.PhatTusServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin(value = "*",allowedHeaders = "*")
@RequestMapping(value = "api/version1.0")
public class PhatTusController {
    @Autowired
    private PhatTusServices phatTusServices;
    // them phat tu
    @RequestMapping(value = "PhatTus/them",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewPhatTu(@RequestBody String ptM){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        PhatTus pt = gson.fromJson(ptM, PhatTus.class);
        return phatTusServices.addNewPhatTu(pt);
    }
    // sua phat tu
    @RequestMapping(value = "PhatTus/sua",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public PhatTus remakePhatTu(@RequestBody String ptS){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        PhatTus hs = gson.fromJson(ptS, PhatTus.class);

        return phatTusServices.remakePhatTu(hs);
    }
    // lọc theo tên
    @RequestMapping(value = "PhatTus/filterName",method = RequestMethod.GET)
    public List<PhatTus> finterName(@RequestParam String name){
        return phatTusServices.filterName(name);
    }
    // lọc theo phap danh
    @RequestMapping(value = "PhatTus/filterPhapDanh",method = RequestMethod.GET)
    public List<PhatTus> filterPhapDanh(@RequestParam String phapDanh){
        return phatTusServices.filterPhapDanh(phapDanh);
    }
    // lọc theo giới tính
    @RequestMapping(value = "PhatTus/filterGioiTinh",method = RequestMethod.GET)
    public List<PhatTus> filterGioiTinh(@RequestParam int gioiTinh){
        return phatTusServices.filterGioiTinh(gioiTinh);
    }
    // loc va phan trang phat tu
    @RequestMapping(value = "PhatTus/pagePhatTu",method = RequestMethod.GET)
    public Page<PhatTus> pagePhatTu( String name,  String phapDanh, String gioiTinh, String trangThai,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        Pageable pageable =  PageRequest.of(page, size);
        return phatTusServices.pagePhatTu(name, phapDanh,gioiTinh,trangThai,pageable);
    }
    // chuc nang doi mk
    @GetMapping("PhatTus/doiMatKhau")
    public String doiMatKhau( @RequestParam String email, String matKhau, String newPassword){
        return phatTusServices.doiMatKhau(email,matKhau,newPassword);
    }
    // tao don dang ki trong bảng đk
    @RequestMapping(value = "PhatTus/addNewDonDangKi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewDonDangKi( String ten, String phapDanh, String email, String sdt,int daoTrangId){
        return phatTusServices.addNewDonDangKi(ten,phapDanh,email,sdt,daoTrangId);
    }
    // xy ly don dang ki trong bảng đk
    @RequestMapping(value = "PhatTus/xuLyDon", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String xuLyDon(@RequestParam int donDangKiId,@RequestParam int trangThai,@RequestParam int nguoiXuLy){
        return phatTusServices.xuLyDon(donDangKiId,trangThai,nguoiXuLy);
    }
}
