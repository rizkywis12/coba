package com.binar.grab.repository;

import com.binar.grab.model.Barang;
import com.binar.grab.model.Supplier;
import com.binar.grab.model.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository //step 2
public interface TransaksiRepository extends
        PagingAndSortingRepository<Transaksi, Long> {

    @Query("select c from Transaksi c WHERE c.id = :id")
    public Transaksi getbyID(@Param("id") Long id);

    public Page<Transaksi> findByBarangNamaLike(String namaBarang , Pageable pageable);

    public Page<Transaksi> findByPembeliNamaLike(String namaPembeli , Pageable pageable);

    @Query("select c from Transaksi c")// nama class
    public Page<Transaksi> getAllData(Pageable pageable);
}