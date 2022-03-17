package com.binar.grab.controller;

import com.binar.grab.model.Mahasiswa;
import com.binar.grab.repository.MahasiswaRepository;
import com.binar.grab.service.MahasiswaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/mhs")
public class MahasiswaController {
    @Autowired
    public MahasiswaInterface serviceMahasiswa;

    @Autowired
    public MahasiswaRepository mahasiswaRepository;

    @PostMapping("")
    public ResponseEntity<Map> save(@RequestBody Mahasiswa objModel
    ) {
        Map save = serviceMahasiswa.insert(objModel);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map> update(@RequestBody Mahasiswa objModel
    ) {
        Map update = serviceMahasiswa.update(objModel);
        return new ResponseEntity<Map>(update, HttpStatus.OK);// response
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id
    ) {
        Map update = serviceMahasiswa.delete(id);
        return new ResponseEntity<Map>(update, HttpStatus.OK);// response
    }

    @GetMapping("")
    public ResponseEntity<Map>  getList() {
        Map map = new HashMap();
        map.put("data",mahasiswaRepository.findAll());
        map.put("code", "200");
        map.put("status", "success");
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> getListData(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) String nim) {
        Pageable show_data = PageRequest.of(page, size);
        Page<Mahasiswa> list = null;
        if (nama != null && nim != null) {
            mahasiswaRepository.getbyNamaAndNim(nama,nim, show_data);
        } else
        if (nama != null) {
            mahasiswaRepository.getbyNama(nama, show_data);
        } else {
            mahasiswaRepository.getAllData(show_data);
        }
        Map map = new HashMap();
        map.put("data", list);
        map.put("code", "200");
        map.put("status", "success");
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }
}
