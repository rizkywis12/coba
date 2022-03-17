package com.binar.grab.service;

import com.binar.grab.model.PerpustakaanDummy;

import java.util.List;
import java.util.Map;

public interface PerpustakaanDummyService {
    //step 2 : mebuat kerangka method
    public Map simpan(PerpustakaanDummy obj);
    public Map update(PerpustakaanDummy obj);
    public PerpustakaanDummy fingDataById(Long id);
    public List<PerpustakaanDummy> list();
    public void delete();
}
