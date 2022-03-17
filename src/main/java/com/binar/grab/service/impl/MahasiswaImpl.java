package com.binar.grab.service.impl;

import com.binar.grab.model.Mahasiswa;
import com.binar.grab.repository.MahasiswaRepository;
import com.binar.grab.service.MahasiswaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MahasiswaImpl implements MahasiswaInterface {

    @Autowired
    public MahasiswaRepository mahasiswaRepository;

    @Override
    public Map insert(Mahasiswa obj) {
        Map map = new HashMap();
        try {
            Mahasiswa save = mahasiswaRepository.save(obj);
            map.put("data", save);
            map.put("code", "200");
            map.put("status", "success");
            return map;
        } catch (Exception e) {
            map.put("code", "500");
            map.put("status", "failed");
            return map;
        }

    }

    @Override
    public Map update(Mahasiswa obj) {
        Map map = new HashMap();
        try {
            Mahasiswa update = mahasiswaRepository.getbyID(obj.getId());
            update.setAlamat(obj.getAlamat());
            Mahasiswa doSave = mahasiswaRepository.save(update);
            map.put("data", doSave);
            map.put("code", "200");
            map.put("status", "success");
        } catch (Exception e) {
            map.put("code", "500");
            map.put("status", "failed");
            return map;
        }
        return map;
    }

    @Override
    public Map delete(Long obj) {
        Map map = new HashMap();
        try {
            Mahasiswa getData = mahasiswaRepository.getbyID(obj);
            mahasiswaRepository.delete(getData);
            map.put("data", "success");
            map.put("code", "200");
            map.put("status", "success");
            return map;
        } catch (Exception e) {
            map.put("code", "500");
            map.put("status", "failed");
            return map;
        }

    }
}
