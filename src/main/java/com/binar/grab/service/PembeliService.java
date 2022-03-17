package com.binar.grab.service;

import com.binar.grab.model.Pembeli;

import java.util.Map;

public interface PembeliService {
    /* relasi one- to one Pembeli dan DetailPembeli
    1. simpan pembeli dan detail
    post : /api/pembeli/simpan
    2. update pembeli dan detail
    put : /api/pembeli/update
    3. list pembeli - filter by nama
    get : /api/pembeli/list
    4. delete sof pembeli -
    delete : /api/pembeli/delete
     */
    public Map simpan(Pembeli obj);

    public Map update(Pembeli obj);

    // list : kita di controller

    public Map delete(Long idPembeli);
}
