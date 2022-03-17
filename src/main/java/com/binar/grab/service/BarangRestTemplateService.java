package com.binar.grab.service;

import com.binar.grab.model.Barang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BarangRestTemplateService {
    public Map insert(Barang barang, Long idsupplier);

    public Map update(Barang barang, Long idsupplier);

    public Map delete(Long barang);

    public Map getAll(int size, int page);
}
