package com.example.quanlyphattudemo.Controller;

import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Models.KieuThanhViens;
import com.example.quanlyphattudemo.Services.ChuasServices;
import com.example.quanlyphattudemo.Services.KieuThanhViensServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

@RestController
@CrossOrigin(value = "*",allowedHeaders = "*")
@RequestMapping(value = "api/version1.0")
public class KieuThanhViensController {
    @Autowired
    private KieuThanhViensServices kieuThanhViensServices;
    // them kieu thanh vien
    @PostMapping(value = "/kieuThanhViens/them")
    public String addNewKieuThanhViens(@RequestBody String kieuThanhViensMoi){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        KieuThanhViens ktv = gson.fromJson(kieuThanhViensMoi, KieuThanhViens.class);
        return kieuThanhViensServices.addNewKieuThanhViens(ktv);
    }
    // sua kieu thanh vien
    @PutMapping(value = "kieuThanhViens/sua")
    public String remakeKieuThanhViens(@RequestBody String kieuThanhViensSua){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        KieuThanhViens ktv = gson.fromJson(kieuThanhViensSua, KieuThanhViens.class);
        return kieuThanhViensServices.remakeKieuThanhViens(ktv);
    }
    // xoa kieu thanh vien
    @RequestMapping(value = "kieuThanhViens/xoa", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public KieuThanhViens removeChuas(@RequestParam int kieuThanhViensId) {
        return kieuThanhViensServices.removeKieuThanhViens(kieuThanhViensId);
    }
    // loc va phan trang kieu thanh vien
    @GetMapping(value = "kieuThanhViens/pagefilterKieuThanhViens")
    public Page<KieuThanhViens> pageFilterKieuThanhViens(String code, String tenKieu, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable =  PageRequest.of(page, size);
        return kieuThanhViensServices.pageFilterKieuThanhViens(code,tenKieu,pageable);
    }
}
