package com.binar.grab.service;

import com.binar.grab.dao.request.TransaksiRequest;
import com.binar.grab.model.Transaksi;

import java.util.Map;

public interface TransaksiService {
    /*
    a. simpan transaksi
    post : /api/transaksi
    b. update transaksi
    put : /api/transaksi
    c. list transaksi : dalam satu API
 	- filter by nama barang
	- filter by nama pembeli
	- filter by tanggal pembelian barang
    get : /api/transaksi
    d. delete soft transaksi
    delete : /api/transaksi
     */

    public Map simpan(TransaksiRequest obj);

    public Map update(TransaksiRequest obj);

    public Map delete(Long obj);

    // list : controller
}
