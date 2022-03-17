package com.binar.grab.service.impl;

import com.binar.grab.model.Barang;
import com.binar.grab.repository.BarangRepository;
import com.binar.grab.service.BarangTymeleafService;
import com.binar.grab.util.BadResourceException;
import com.binar.grab.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
1. buat pakcgae impl
2. buat class BarangImpl di imple dari intercafe class
3. implement method
 */
@Service //service wajib
@Transactional // opsional :
public class BarangTymeleafImpl implements BarangTymeleafService {
    // // IOC DI
    @Autowired
    public BarangRepository repo;

    @Override
    public List<Barang> listBarangTymeleaf(int pageNumber, int ROW_PER_PAGE ) {
        List<Barang> barangs = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, ROW_PER_PAGE,
                Sort.by("id").ascending());
        repo.findAll(sortedByIdAsc).forEach(barangs::add);
        return  barangs;

    }

    @Override
    public boolean existsByIdTymeleaf(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Barang findByIdTymeleaf(Long id) throws ResourceNotFoundException {
        Barang barang = repo.findById(id).orElse(null);
        if (barang==null) {
            throw new ResourceNotFoundException("Cannot find barang with id: " + id);
        }
        else return barang;
    }

    @Override
    public Barang saveTymeleaf(Barang barang) throws BadResourceException {
//        if (!StringUtils.isEmpty(barang.getNama())) {
            return repo.save(barang);
//        }
//        else {
//            BadResourceException exc = new BadResourceException("Failed to save barang");
//            exc.addErrorMessage("barang is null or empty");
//            throw exc;
//        }
    }

    @Override
    public void updateTymeleaf(Barang barang) throws ResourceNotFoundException, BadResourceException {
//        if (!StringUtils.isEmpty(barang.getNama())) {
            if (!existsByIdTymeleaf(barang.getId())) {
                throw new ResourceNotFoundException("Cannot find Barang with id: " + barang.getId());
            }
            repo.save(barang);
//        }
//        else {
//            BadResourceException exc = new BadResourceException("Failed to save Barang");
//            exc.addErrorMessage("Barang is null or empty");
//            throw exc;
//        }
    }

    @Override
    public void deleteByIdTymeleaf(Long id) throws ResourceNotFoundException {
        if (!existsByIdTymeleaf(id)) {
            throw new ResourceNotFoundException("Cannot find barang with id: " + id);
        }
        else {
            Barang a = repo.getbyID(id);
            a.setDeleted_date(new Date());
            repo.save(a);
        }
    }



}
