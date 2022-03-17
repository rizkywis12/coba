package com.binar.grab.service;

import com.binar.grab.model.Barang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BarangService {
    public Map insert(Barang barang, Long idsupplier);

    public Map update(Barang barang, Long idsupplier);

    public Map delete(Long barang);

    public Map getAll(int size, int page);

    public Map findByNama(String nama, Integer page, Integer size);

    Page<Barang> findByNamaLike(String nama, Pageable pageable);
}
