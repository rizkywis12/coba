package com.binar.grab.repository;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Pembeli;
import com.binar.grab.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository //step 2
public interface PembeliRepository extends
        PagingAndSortingRepository<Pembeli, Long> {

    @Query("select c from Pembeli c WHERE c.id = :id")
    public Pembeli getbyID(@Param("id") Long id);

    @Query("select c from Pembeli c ")// nama class
    public Page<Pembeli> getAllData(Pageable pageable);

    @Query("select c from Pembeli c where c.nama like :nama")// nama class
    public Page<Pembeli> findByNama(String nama , Pageable pageable);
}