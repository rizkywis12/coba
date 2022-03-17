package com.binar.grab.service.impl;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Pembeli;
import com.binar.grab.model.PembeliDetail;
import com.binar.grab.model.Supplier;
import com.binar.grab.repository.PembeliDetailRepository;
import com.binar.grab.repository.PembeliRepository;
import com.binar.grab.service.PembeliService;
import com.binar.grab.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PembeliImpl implements PembeliService {

    public static final Logger log = LoggerFactory.getLogger(PembeliImpl.class);


    @Autowired
    public PembeliDetailRepository pembeliDetailRepository;

    @Autowired
    public PembeliRepository pembeliRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map simpan(Pembeli request) {
        try {
            /*
{
    "nama":"nama 2",
    "pembeliDetail" :{
        "hp":"",
        "jk":"",
        "alamat":""
    }
}
             */
            /*
            1. validasi
            2. chek detail
            3. insert pembeli
            4. insert pembeli detail
             */
//            1. validasi
            if (templateResponse.chekNull(request.getNama())) {
                return templateResponse.templateEror("Nama is Requiered");
            }
//            2. chek detail
            if (templateResponse.chekNull(request.getPembeliDetail())) {
                return templateResponse.templateEror("Pembeli Detail is Requiered");
            }
            if (templateResponse.chekNull(request.getPembeliDetail().getHp())) {
                return templateResponse.templateEror("Hp is Requiered");
            }
//            3. insert pembeli
            Pembeli pembeliData = new Pembeli();
            pembeliData.setNama(request.getNama());
            Pembeli pembeli = pembeliRepository.save(pembeliData);
//            4. insert pembeli detail : set FK dari pembeli
            PembeliDetail pembeliDetailData = new PembeliDetail();
            pembeliDetailData.setHp(request.getPembeliDetail().getHp());
            pembeliDetailData.setPembeli(pembeli);
            pembeliDetailData.setAlamat(request.getPembeliDetail().getAlamat());
            pembeliDetailData.setJk(request.getPembeliDetail().getJk());
            PembeliDetail doUpdateDetailPembeli = pembeliDetailRepository.save(pembeliDetailData);

            return  templateResponse.templateSukses(pembeli);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map update(Pembeli request) {
        try {
            /*
{
   "id":"1",
    "nama":"nama 2",
    "pembeliDetail" :{
        "id":"1",
        "hp":"",
        "jk":"",
        "alamat":""
    }
}
             */
            /*
            1. validasi
            2. chek detail
            3. update pembeli
            4. update pembeli detail
             */
//            1. validasi
            if (templateResponse.chekNull(request.getNama())) {
                return templateResponse.templateEror("Nama is Requiered");
            }
//            2. chek detail
            if (templateResponse.chekNull(request.getPembeliDetail())) {
                return templateResponse.templateEror("Pembeli Detail is Requiered");
            }
            if (templateResponse.chekNull(request.getPembeliDetail().getHp())) {
                return templateResponse.templateEror("Hp is Requiered");
            }
            //chekl id pembeli
            Pembeli pembeliUpdate = pembeliRepository.getbyID(request.getId());
            if (templateResponse.chekNull(pembeliUpdate)) {
                return templateResponse.templateEror("Pembeli tidak ada di database");
            }
//            3. update pembeli
            pembeliUpdate.setNama(request.getNama());
            pembeliUpdate.getPembeliDetail().setHp(request.getPembeliDetail().getHp());
            pembeliUpdate.getPembeliDetail().setAlamat(request.getPembeliDetail().getAlamat());
            pembeliUpdate.getPembeliDetail().setJk(request.getPembeliDetail().getJk());
//            4. do update ke database
            Pembeli doUpdateDetailPembeli = pembeliRepository.save(pembeliUpdate);

            return  templateResponse.templateSukses(doUpdateDetailPembeli);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map delete(Long idPembeli) {
     /*
        soft delete , bukan delete permanent
        1. chek id idPembeli
        2. update , tanggal deleted saja
         */
        try {
            if (templateResponse.chekNull(idPembeli)) {
                return templateResponse.templateEror("Id Pembeli is required");
            }
            //            1. chek id idPembeli
            Pembeli chekIdPembeli = pembeliRepository.getbyID(idPembeli);
            if (templateResponse.chekNull(chekIdPembeli)) {
                return templateResponse.templateEror("Id Pembeli Not found");
            }

//            2. update , tanggal deleted saja
            chekIdPembeli.setDeleted_date(new Date());//
            pembeliRepository.save(chekIdPembeli);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }
}
