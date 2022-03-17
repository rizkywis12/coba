package com.binar.grab.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "barang")
@Where(clause = "deleted_date is null")
public class Barang extends AbstractDate implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", nullable = false, length = 45)
    private String nama;

    @Column(name = "stok", nullable = false, length = 11)
    private int stok;

    @Column(name = "satuan", nullable = false, length = 45)
    private String satuan;

    @Column(name = "harga",length = 11)
    private Double harga;

    // wajib
    @ManyToOne(targetEntity = Supplier.class, cascade = CascadeType.ALL)
    private Supplier supplier;//ok supplier_id


}
