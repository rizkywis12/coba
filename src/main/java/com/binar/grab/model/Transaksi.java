package com.binar.grab.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "transaksi")
@Where(clause = "deleted_date is null")
public class Transaksi  extends AbstractDate implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    Barang barang;

    @ManyToOne
    @JoinColumn(name = "pembeli_id")
    Pembeli pembeli;

    private Double harga;

    private Integer qty;

}

