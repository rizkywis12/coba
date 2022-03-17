package com.binar.grab.service.impl;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Mahasiswa;
import com.binar.grab.model.Supplier;
import com.binar.grab.repository.BarangRepository;
import com.binar.grab.repository.SupplierRepository;
import com.binar.grab.service.BarangService;
import com.binar.grab.util.TemplateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BarangImpl implements BarangService {


    //1. call repository banrang dan supplierbi
    @Autowired
    public BarangRepository barangRepository;

    public static final Logger log = LoggerFactory.getLogger(BarangImpl.class);


    @Autowired
    public SupplierRepository supplierRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map insert(Barang obj, Long idsupplier) {
        try {
            if (templateResponse.chekNull(obj.getNama())) {
                return templateResponse.templateEror("Nama is Requiered");
            }

            if (templateResponse.chekNull(obj.getHarga())) {
                return templateResponse.templateEror("Harga is requiered");
            }

            if (templateResponse.chekNull(idsupplier)) {
                return templateResponse.templateEror("Id Supplier is requiered");
            }
            Supplier chekId = supplierRepository.getbyID(idsupplier);
            if (templateResponse.chekNull(chekId)) {
                return templateResponse.templateEror("Id Supplier NOt found");
            }
            //do save
            obj.setSupplier(chekId);
            Barang barangSave = barangRepository.save(obj);
            return templateResponse.templateSukses(barangSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map getAll(int size, int page) {
        try {
            Pageable show_data = PageRequest.of(page, size);
            Page<Barang> list = barangRepository.getAllData(show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {
            log.error("ada eror di method getAll:" + e);
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Map update(Barang barangReq, Long idsupplier) {
        try {
/*
1. chek id supplirt - =takutnyanya suppliar tidak ada
2. chek id barang - apakah ada atau ga di db
3. simpan ke database
 */
            if (templateResponse.chekNull(idsupplier)) {
                return templateResponse.templateEror("Id Supplier is requiered");
            }
//            1. che id supplirt - =takutnyanya suppliar
            Supplier chekId = supplierRepository.getbyID(idsupplier);
            if (templateResponse.chekNull(chekId)) {
                return templateResponse.templateEror("Id Supplier N0t found");
            }
            if (templateResponse.chekNull(barangReq.getId())) {
                return templateResponse.templateEror("Id Barang is required");
            }
//            2. chek id barang - apakah ada atau ga
            Barang chekIdBarang = barangRepository.getbyID(barangReq.getId());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Not found");
            }
//            3. simpan database : update
            chekIdBarang.setNama(barangReq.getNama());
            chekIdBarang.setHarga(barangReq.getHarga());
            chekIdBarang.setStok(barangReq.getStok());
            chekIdBarang.setSatuan(barangReq.getSatuan());
            Barang dosave = barangRepository.save(chekIdBarang);

            return templateResponse.templateSukses(dosave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }

    }

    @Override
    public Map delete(Long barang) {
        /*
        soft delete , bukan delete permanent
        1. chek id barang
        2. update , tanggal deleted saja
         */
        try {
            if (templateResponse.chekNull(barang)) {
                return templateResponse.templateEror("Id Barang is required");
            }
            //            1. chek id barang
            Barang chekIdBarang = barangRepository.getbyID(barang);
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Not found");
            }

//            2. update , tanggal deleted saja
            chekIdBarang.setDeleted_date(new Date());//
            barangRepository.save(chekIdBarang);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Map findByNama(String nama, Integer page, Integer size) {
        try {
           /*
           1. buat query dulu where nama barang
            */
            Pageable show_data = PageRequest.of(page, size);
            Page<Barang> list = barangRepository.findByNama(nama, show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {

            log.error("eror disini findByNama : " + e);
            //menampilkan responose
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Page<Barang> findByNamaLike(String nama, Pageable pageable) {
        try {
           /*
           1. buat query dulu where nama barang = like
            */
            Page<Barang> list = barangRepository.findByNamaLike("'%" + nama + "%'", pageable);
//             public Page<Barang> findByNamaLike(String nama , Pageable pageable);
            return list;
        } catch (Exception e) {
            // manampilkan di terminal saja
            log.error("ada eror di method findByNamaLike:" + e);
            return null;
        }
    }
}
