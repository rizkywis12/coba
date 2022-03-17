package com.binar.grab.service;



import com.binar.grab.model.Mahasiswa;

import java.util.Map;

public interface MahasiswaInterface {
    public Map insert(Mahasiswa obj);
    public Map update(Mahasiswa obj);
    public Map delete(Long obj);
}
