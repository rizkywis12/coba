package com.binar.grab.service.impl;

import com.binar.grab.model.BarangDummy;
import com.binar.grab.service.BarangDummyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BarangDummyImpl implements BarangDummyService {

   static List<BarangDummy> barangDummyStatic = new ArrayList<>();
    @Override
    public Map save(BarangDummy barangDummy) {
        Map map = new HashMap();
        map.put("data", barangDummy);
        map.put("status","sukses");
        map.put("message","200");
        barangDummyStatic.add(barangDummy);
        return map;
    }

    @Override
    public List<BarangDummy> list() {
        Map map = new HashMap();
        map.put("data", barangDummyStatic);
        map.put("status","sukses");
        map.put("message","200");
        return barangDummyStatic;
    }

    @Override
    public Map update(BarangDummy barangDummy) {
        BarangDummy update = new BarangDummy();
        for(BarangDummy obj : barangDummyStatic){
            if(obj.getId() == barangDummy.getId()){
                update.setId(obj.getId());
                update.setHarga(obj.getHarga());
                update.setNama(obj.getNama());
                barangDummyStatic.remove(obj);
                barangDummyStatic.add(update);
            }
        }
        Map map = new HashMap();
        map.put("data",update);
        map.put("status","sukses");
        map.put("message","200");
        return map;
    }

    @Override
    public void delete(Long id) {
        for(BarangDummy obj : barangDummyStatic){
            if(obj.getId() == id){
                barangDummyStatic.remove(obj);
            }
        }
    }
}
