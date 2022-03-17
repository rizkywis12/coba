package com.binar.grab.junit;

import com.binar.grab.model.Barang;
import com.binar.grab.service.BarangRestTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BarangJunitTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BarangRestTemplateService barangRestTemplateService;

    @Test
    public void contohJunit() {
        int a = 5;
        int b = 10;
        int c = a + b;
        assertEquals(15, c);

    }

    @Test
    public void contohJunit2() {
        int a = 5;
        int b = 10;
        int c = a * b;
        assertEquals(50, c);
    }

    @Test
    public void simpanBarang() {
        Barang req = new Barang();
        req.setHarga(200.0);
        req.setNama("riski wisesar");
        req.setStok(100);
        req.setSatuan("kg");

        Long idSupp = 1L;
        Map map = barangRestTemplateService.insert(req, idSupp);
        assertEquals("200", map.get("status"));
        if (map.get("status").equals("200")) {
            System.out.println(map.get("data"));
            System.out.println(map.get("status"));
            System.out.println(map.get("message"));
        } else {
            System.out.println("terjadi eror");
        }
    }

    @Test
    public void updateBarang() {
        Barang req = new Barang();
        req.setId(6L);
        req.setHarga(200.0);
        req.setNama("riski wisesar ubah");
        req.setStok(1001);
        req.setSatuan("kg");

        Long idSupp = 1L;
        Map map = barangRestTemplateService.update(req, idSupp);
        assertEquals("200", map.get("status"));
        if (map.get("status").equals("200")) {
            System.out.println(map.get("data"));
            System.out.println(map.get("status"));
            System.out.println(map.get("message"));
        } else {
            System.out.println("terjadi eror");
        }

    }

    @Test
    public void listBarang() {
        Map map = barangRestTemplateService.getAll(10, 0);
        assertEquals("200", map.get("status"));
        if (map.get("status").equals("200")) {
            System.out.println(map.get("data"));
            System.out.println(map.get("status"));
            System.out.println(map.get("message"));
        } else {
            System.out.println("terjadi eror");
        }
    }


}
