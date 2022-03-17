package com.binar.grab.repository;

import com.binar.grab.model.Mahasiswa;
import com.binar.grab.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository //step 2
public interface SupplierRepository extends
        PagingAndSortingRepository<Supplier, Long> {

    @Query("select c from Supplier c WHERE c.id = :id")
    public Supplier getbyID(@Param("id") Long id);
}