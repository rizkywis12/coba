package com.binar.grab.controller;

import com.binar.grab.model.PerpustakaanDummy;
import com.binar.grab.service.PerpustakaanDummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/binar/book/")// localhost :8080/v1/binar/book
public class PerpustakaanDummyController {
    //langkah 4 : membuat controller-langkah terkahir
    @Autowired //DI
    public PerpustakaanDummyService perpustakaanDummyService;

    @PostMapping("save")
    public ResponseEntity<Map> save(
            @RequestBody PerpustakaanDummy objModel) {
        Map obj = perpustakaanDummyService.simpan(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);// response
    }

    @PutMapping("update")
    public ResponseEntity<Map> update(
            @RequestBody PerpustakaanDummy objModel) {
        Map obj = perpustakaanDummyService.update(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);// response
    }

    @GetMapping("list")
    @ResponseBody
    public ResponseEntity<List<PerpustakaanDummy>> getList() {
        List<PerpustakaanDummy> c = perpustakaanDummyService.list();
        return new ResponseEntity<List<PerpustakaanDummy>>(c, HttpStatus.OK);
    }

}
