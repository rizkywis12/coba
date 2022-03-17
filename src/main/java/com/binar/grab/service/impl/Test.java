package com.binar.grab.service.impl;

import com.binar.grab.model.Barang;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Test {
//    https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
    public  static  void main(String[] args){


    }

    public static void list(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final  String url = "http://localhost:8082/api/v1/binar/barang/list?size={size}&page={page}";
        Map<String, Object> params = new HashMap<>();
        params.put("size", 10);
        params.put("page", 0);
        HttpEntity<?> httpEntity  = new HttpEntity<>(httpHeaders);
        RestTemplateBuilder restTemplate = new RestTemplateBuilder();
        ResponseEntity<Map> result = restTemplate.build().exchange(url, HttpMethod.GET, httpEntity,Map.class, params);

    }

    public static void simpan(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        String url = "http://localhost:8082/api/v1/binar/barang/save/"+1;
        Barang obj = new Barang();
        obj.setSatuan("kg");
        obj.setStok(100);
        obj.setNama("nama 122");
        obj.setHarga(1234.0);
        ResponseEntity<Map> result = restTemplateBuilder.build().postForEntity(url, obj, Map.class);
        System.out.println(result.getBody());
    }
    public static void update (){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        String url = "http://localhost:8082/api/v1/binar/barang/update/"+1;
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        // create a Barang object
        Barang obj = new Barang();
        obj.setId(5L);
        obj.setSatuan("kg");
        obj.setStok(100);
        obj.setNama("nama 122 ubah saya");
        obj.setHarga(1234554.0);

        // build the request
        HttpEntity<Barang> entity = new HttpEntity<>(obj, headers);

        // send PUT request to update post with `id` 10
        ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.PUT, entity, Map.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
        } else {
            System.out.println("eror");
        }
    }
    public static void delete(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Long id = 5L;
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        ResponseEntity<String> exchange = restTemplateBuilder.build().exchange("http://localhost:8082/api/v1/binar/barang/delete/"+id , HttpMethod.DELETE, entity, String.class);

        System.out.println("response   barang="+exchange.getBody());
    }
}
