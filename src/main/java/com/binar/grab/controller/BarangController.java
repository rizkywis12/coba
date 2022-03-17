package com.binar.grab.controller;

import com.binar.grab.config.Config;
import com.binar.grab.model.Barang;
import com.binar.grab.model.Supplier;
import com.binar.grab.model.oauth.User;
import com.binar.grab.repository.BarangRepository;
import com.binar.grab.repository.oauth.UserRepository;
import com.binar.grab.service.SupplierService;
import com.binar.grab.service.oauth.Oauth2UserDetailsService;
import com.binar.grab.util.SimpleStringUtils;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import com.binar.grab.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/binar/barang")
public class BarangController {

    @Autowired
    public BarangService barangService;

    Config config = new Config();

    @Autowired
    private Oauth2UserDetailsService userDetailsService;

    @Autowired
    public BarangRepository barangRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public UserRepository userRepository;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();


    @GetMapping("list-barang")// by seller
    public ResponseEntity<Map> listNotif(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String nama,// BUY OR SELL : menentukan siapa yang akses
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype,
            Principal principal) {
    /*
    1.principal : mendapatkan username berdasarkan token user yang akses di client
    2.  getShort : shoritng
     */
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<Barang> list = null;

        User idUser = getUserIdToken(principal, userDetailsService);
        if (idUser == null) {
            return new ResponseEntity<Map>(templateResponse.notFound("User id notfound"), HttpStatus.NOT_FOUND);
        }
        if (nama != null && priceMin !=null && priceMax != null ) {
            list = barangRepository.getDataByPriceAndNama(priceMin,priceMax, "'%" + nama + "%'",show_data);
        } else if ( priceMin !=null && priceMax != null ) {
            list = barangRepository.getDataByPrice(priceMin,priceMax, show_data);
        } else if (nama != null ) {
            list = barangRepository.findByNamaLike("'%" + nama + "%'", show_data);
        } else {
            list = barangRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }

    public User getUserIdToken(Principal principal, Oauth2UserDetailsService userDetailsService) {
        UserDetails user = null;
        String username = principal.getName();
        if (!StringUtils.isEmpty(username)) {
            user = userDetailsService.loadUserByUsername(username);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        User idUser = userRepository.findOneByUsername(user.getUsername());
        if (null == idUser) {
            throw new UsernameNotFoundException("User name not found");
        }
        return idUser;

    }




}

