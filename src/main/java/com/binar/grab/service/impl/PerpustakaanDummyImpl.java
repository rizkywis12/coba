package com.binar.grab.service.impl;

import com.binar.grab.model.PerpustakaanDummy;
import com.binar.grab.service.PerpustakaanDummyService;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service // WAJIB
public class PerpustakaanDummyImpl implements PerpustakaanDummyService {
    // step 3 : membuat logic
    /*
    1. logic dari metghod disini
    2. db , simpan update.
     */

    @Autowired // DI / dependecy injekction
    public TemplateResponse templateResponse;

    // ini database
    public static List<PerpustakaanDummy> dataList = new ArrayList<>();

    @Override
    public Map simpan(PerpustakaanDummy obj) {
        try {
            dataList.add(obj); // nambahin data
            return templateResponse.templateSukses(obj);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map update(PerpustakaanDummy obj) { // request
        try {
            if (obj.getId() == null) {
                return templateResponse.templateEror("id is required");
            }
            PerpustakaanDummy updateData = null;
            for (PerpustakaanDummy data : dataList) {
                if (data.getId() == obj.getId()) {
                    updateData = new PerpustakaanDummy(
                            data.getId(),
                            obj.getJudul(),
                            obj.getPenerbit(),
                            obj.getPenulis(),
                            obj.getEditor(),
                            obj.getQty()
                    );
                    dataList.add(updateData);// buat udpate data
                    dataList.remove(data); // buat remove
                }
            }
            return templateResponse.templateSukses(updateData);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public PerpustakaanDummy fingDataById(Long id) {
        return null;
    }

    @Override
    public List<PerpustakaanDummy> list() {
        return dataList;
    }

    @Override
    public void delete() {

    }
}
