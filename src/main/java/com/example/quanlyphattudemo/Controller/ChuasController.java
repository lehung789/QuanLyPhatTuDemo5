package com.example.quanlyphattudemo.Controller;

import com.example.quanlyphattudemo.Models.Chuas;
import com.example.quanlyphattudemo.Models.DaoTrangs;
import com.example.quanlyphattudemo.Repository.ChuasRepository;
import com.example.quanlyphattudemo.Services.ChuasServices;
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
public class ChuasController {
    @Autowired
    private ChuasServices chuasServices;
    // them chua
    @PostMapping(value = "/chuas/them")
    public String addNewChua(@RequestBody String chuasMoi){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        Chuas c = gson.fromJson(chuasMoi, Chuas.class);
        return chuasServices.addNewChua(c);
    }
    // sua chua
    @PutMapping(value = "chuas/sua")
    public String remakeDaoTrangs(@RequestBody String chuasSua){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        Chuas c = gson.fromJson(chuasSua, Chuas.class);
        return chuasServices.remakeChuas(c);
    }
    // xoa chua
    @RequestMapping(value = "chuas/xoa", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Chuas removeChuas(@RequestParam int chuasId) {
        return chuasServices.removeChuas(chuasId);
    }
    // loc va phan trang dao trang
    @GetMapping(value = "chuas/pagefilterChuas")
    public Page<Chuas> pageFilterChuas(String tenChua, String ngayThanhLap, String diaChi, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable =  PageRequest.of(page, size);
        return chuasServices.pageFilterChuas(tenChua,ngayThanhLap,diaChi,pageable);
    }
}
