package com.binar.grab.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MahasiswaTesting {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void saveMahasiswa() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        String bodyTesting = "{\n" +
                "    \"nama\":\"jhon\",\n" +
                "    \"nim\":\"1234567891\",\n" +
                "    \"alamat\":\"jakarta\"\n" +
                "    \n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);
        ResponseEntity<String> exchange = restTemplate.exchange
                ("http://localhost:8081/api/v1/mhs", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());
    }

    @Test
    public void updateMahasiswa() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        String bodyTesting = "{   \n" +
                "    \"id\":\"1\",\n" +
                "    \"nama\":\"jhon\",\n" +
                "    \"nim\":\"1234567891\",\n" +
                "    \"alamat\":\"jakarta barat\"\n" +
                "    \n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/v1/mhs", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());
    }

    @Test
    public void deleteMahasiswa() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Long id = 2L;
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/v1/mhs/" + id, HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());
    }

    @Test
    public void listMahasiswa() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/v1/mhs", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());
    }
}
