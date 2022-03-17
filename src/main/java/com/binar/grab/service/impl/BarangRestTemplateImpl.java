package com.binar.grab.service.impl;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Supplier;
import com.binar.grab.repository.BarangRepository;
import com.binar.grab.repository.SupplierRepository;
import com.binar.grab.service.BarangRestTemplateService;
import com.binar.grab.service.BarangService;
import com.binar.grab.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BarangRestTemplateImpl implements BarangRestTemplateService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    //1. call repository banrang dan supplierbi
    @Autowired
    public BarangRepository barangRepository;

    public static final Logger log = LoggerFactory.getLogger(BarangRestTemplateImpl.class);


    @Autowired
    public SupplierRepository supplierRepository;

    @Autowired
    public TemplateResponse templateResponse;

    /*
    kita ingin call rest kita sendiri: barang
    a. post localhost:8082/api/v1/binar/barang/save/{idsupplier}
    b. put localhost:8082/api/v1/binar/barang/update/{idsupplier}
    c. delete localhost:8082/api/v1/binar/barang/delete/{id}
    d. get localhost:8082/api/v1/binar/barang/list

     */

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

            //do save : yang kita ganti
            String url = "http://localhost:8082/api/v1/binar/barang/save/"+idsupplier;

            ResponseEntity<Map> result = restTemplateBuilder.build().postForEntity(url, obj, Map.class);

            return templateResponse.templateSukses(result.getBody());
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map getAll(int size, int page) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            final  String url = "http://localhost:8082/api/v1/binar/barang/list?size={size}&page={page}";
            Map<String, Object> params = new HashMap<>();
            params.put("size", 10);
            params.put("page", 0);
            HttpEntity<?> httpEntity  = new HttpEntity<>(httpHeaders);
            ResponseEntity<Map> result = restTemplateBuilder.build().exchange(url, HttpMethod.GET, httpEntity,Map.class, params);
            return templateResponse.templateSukses(result.getBody());
        } catch (Exception e) {
            log.error("ada eror di method getAll:" + e);
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Map update(Barang barangReq, Long idsupplier) {
        try {
            if (templateResponse.chekNull(idsupplier)) {
                return templateResponse.templateEror("Id Supplier is requiered");
            }

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            String url = "http://localhost:8082/api/v1/binar/barang/update/"+1;
            HttpHeaders headers = new HttpHeaders();
            // set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
            // set `accept` header
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            // build the request
            HttpEntity<Barang> entity = new HttpEntity<>(barangReq, headers);

            // send PUT request to update post with `id` 10
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.PUT, entity, Map.class);

            // check response status code
            if (response.getStatusCode() == HttpStatus.OK) {
                return templateResponse.templateSukses(response.getBody());
            } else {
                System.out.println("eror");
                return templateResponse.templateEror(response.getBody());
            }


        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }

    }

    @Override
    public Map delete(Long barang) {
        try {
            if (templateResponse.chekNull(barang)) {
                return templateResponse.templateEror("Id Barang is required");
            }
            //            1. chek id barang
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "*/*");
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);

            Long id = 5L;
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            ResponseEntity<Map> exchange = restTemplateBuilder.build().exchange("http://localhost:8082/api/v1/binar/barang/delete/"+id , HttpMethod.DELETE, entity, Map.class);

            System.out.println("response   barang="+exchange.getBody());

            return templateResponse.templateSukses(exchange.getBody());

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }
}
