package com.binar.grab.service.impl;

import com.binar.grab.dao.request.TransaksiRequest;
import com.binar.grab.model.Barang;
import com.binar.grab.model.Pembeli;
import com.binar.grab.model.Supplier;
import com.binar.grab.model.Transaksi;
import com.binar.grab.repository.BarangRepository;
import com.binar.grab.repository.PembeliRepository;
import com.binar.grab.repository.TransaksiRepository;
import com.binar.grab.service.TransaksiService;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TransaksiImpl implements TransaksiService {

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public TransaksiRepository transaksiRepository;

    @Autowired
    public BarangRepository barangRepository;

    @Autowired
    public PembeliRepository pembeliRepository;
    @Override
    public Map simpan(TransaksiRequest request) {
        try {
            /*
            req 1 : ini yang saya mau request, maknayy saya bikin DAO : untuk requesnya
            id barang,
            id pembeli
            qty

            req2
            barang : {
            id:""
            },
            pembeli :  {
            "id":"1"
            }
             */
            if(templateResponse.chekNull(request.getIdBarang())){
                return   templateResponse.templateEror("Id Barang Tidak boleh null");
            }

            if(templateResponse.chekNull(request.getIdPembeli())){
                return   templateResponse.templateEror("Id Pembeli Tidak boleh null");
            }
            Barang chekIdBarang =  barangRepository.getbyID(request.getIdBarang());
            if(templateResponse.chekNull(chekIdBarang)){
                return   templateResponse.templateEror("Id Barang Tidak ada di database");
            }

            Pembeli chekIdPembeli =  pembeliRepository.getbyID(request.getIdPembeli());
            if(templateResponse.chekNull(chekIdBarang)){
                return   templateResponse.templateEror("Id Pembeli Tidak ada di database");
            }

            //disimpan ke db: objek transaksi
            Transaksi objSave = new Transaksi();
            objSave.setBarang(chekIdBarang);
            objSave.setPembeli(chekIdPembeli);
            objSave.setHarga(chekIdBarang.getHarga());
            objSave.setQty(request.getQty());
            Transaksi doSave = transaksiRepository.save(objSave);
            return templateResponse.templateSukses(doSave);
        }catch (Exception e){
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map update(TransaksiRequest request) {
        try {
            /*
            id
            id barang, // yang hanya ini yang diupdate: harga, qty, barang
            id pembeli
            qty
             */

            if(templateResponse.chekNull(request.getIdBarang())){
                return   templateResponse.templateEror("Id Barang Tidak boleh null");
            }

            if(templateResponse.chekNull(request.getIdPembeli())){
                return   templateResponse.templateEror("Id Pembeli Tidak boleh null");
            }
            if(templateResponse.chekNull(request.getId())){
                return   templateResponse.templateEror("Id Transaksi Tidak boleh null");
            }

            Barang chekIdBarang =  barangRepository.getbyID(request.getIdBarang());
            if(templateResponse.chekNull(chekIdBarang)){
                return   templateResponse.templateEror("Id Barang Tidak ada di database");
            }

            Pembeli chekIdPembeli =  pembeliRepository.getbyID(request.getIdPembeli());
            if(templateResponse.chekNull(chekIdPembeli)){
                return   templateResponse.templateEror("Id Pembeli Tidak ada di database");
            }

            Transaksi chekIdTransaksi =  transaksiRepository.getbyID(request.getId());
            if(templateResponse.chekNull(chekIdBarang)){
                return   templateResponse.templateEror("Id Transaksi Tidak ada di database");
            }
            //update disini
            chekIdTransaksi.setBarang(chekIdBarang);
            chekIdTransaksi.setHarga(chekIdBarang.getHarga());
            chekIdTransaksi.setQty(request.getQty());
            Transaksi doSave = transaksiRepository.save(chekIdTransaksi);
            return templateResponse.templateSukses(doSave);
        }catch (Exception e){
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map delete(Long obj) {
      /*
        soft delete , bukan delete permanent
        1. chek id barang
        2. update , tanggal deleted saja
         */
        try {
            if (templateResponse.chekNull(obj)) {
                return templateResponse.templateEror("Id Transaksi is required");
            }
            //            1. chek id barang
            Transaksi chekIdTransaksi = transaksiRepository.getbyID(obj);
            if (templateResponse.chekNull(chekIdTransaksi)) {
                return templateResponse.templateEror("Id Transksi Not found");
            }

//            2. update , tanggal deleted saja
            chekIdTransaksi.setDeleted_date(new Date());//
            transaksiRepository.save(chekIdTransaksi);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }
}
