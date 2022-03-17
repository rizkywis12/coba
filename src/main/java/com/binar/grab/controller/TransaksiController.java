package com.binar.grab.controller;

import com.binar.grab.dao.request.TransaksiRequest;
import com.binar.grab.model.Barang;
import com.binar.grab.model.Pembeli;
import com.binar.grab.model.Transaksi;
import com.binar.grab.repository.TransaksiRepository;
import com.binar.grab.service.BarangService;
import com.binar.grab.service.TransaksiService;
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
@RequestMapping("/v1/binar/transaksi")
public class TransaksiController {

    @Autowired
    public TransaksiService transaksiService;

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public TransaksiRepository transaksiRepository;
    @PostMapping("")
    public ResponseEntity<Map> save(  @RequestBody TransaksiRequest objModel) {
        Map obj = transaksiService.simpan(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map> update(  @RequestBody TransaksiRequest objModel ) {
        Map map = transaksiService.update(objModel);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = transaksiService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByBama(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String  namaBarang,// ga mandatory : default mandatory
            @RequestParam(required = false) String namPembeli) {
        Map map = new HashMap();
        Page<Transaksi> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());//batasin roq

        if(namaBarang != null && !namaBarang.isEmpty()){
            list = transaksiRepository.findByBarangNamaLike("%"+namaBarang+"%",show_data);
        } if(namPembeli != null && !namPembeli.isEmpty()){
            list = transaksiRepository.findByPembeliNamaLike("%"+namPembeli+"%",show_data);
        } else{
            list = transaksiRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }





}
