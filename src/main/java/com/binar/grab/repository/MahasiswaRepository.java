package com.binar.grab.repository;

import com.binar.grab.model.Mahasiswa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends
        PagingAndSortingRepository<Mahasiswa, Long> {

    @Query("select c from Mahasiswa c WHERE c.id = :id")
    public Mahasiswa getbyID(@Param("id") Long id);

    @Query("select c from Mahasiswa c WHERE c.nama = :nama")// nama class
    Page<Mahasiswa> getbyNama(String nama, Pageable pageable);

    @Query("select c from Mahasiswa c")// nama class
    Page<Mahasiswa> getAllData(Pageable pageable);

    @Query("select c from Mahasiswa c WHERE c.nama = :nama and c.nim = :nim ")
    Page<Mahasiswa> getbyNamaAndNim(String nama, String nim, Pageable pageable);
}