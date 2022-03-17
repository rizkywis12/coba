package com.binar.grab.controller;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Pembeli;
import com.binar.grab.repository.PembeliRepository;
import com.binar.grab.service.PembeliService;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/binar/pembeli")
public class PembeliController {

    @Autowired
    public PembeliRepository pembeliRepository;

    @Autowired
    public PembeliService pembeliService;

    @Autowired
    public TemplateResponse templateResponse;

    @PostMapping("/save/")
    public ResponseEntity<Map> save(@RequestBody Pembeli objModel) {
        Map map = new HashMap();
        Map obj = pembeliService.simpan(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Map> update(@RequestBody Pembeli objModel){
        Map obj = pembeliService.update(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = pembeliService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByBama(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama) {
        Map map = new HashMap();
        Page<Pembeli> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());//batasin roq

        if(nama != null && !nama.isEmpty()){
            list = pembeliRepository.findByNama("%"+nama+"%",show_data);
        }else{
            list = pembeliRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }


}

