package com.binar.grab.controller;

import com.binar.grab.model.Supplier;
import com.binar.grab.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/binar/supplier")
public class SupplierController {

    @Autowired
    public SupplierService  supplierService;

    @PostMapping("")
    public ResponseEntity<Map> save(@RequestBody Supplier objModel) {
        Map obj = supplierService.insert(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }
}
