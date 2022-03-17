package com.binar.grab.model
        ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", nullable = false, length = 45)
    private String nama;

    @Column(name = "hp", nullable = false, length = 15)
    private String hp;

    @Column(name = "alamat", columnDefinition="TEXT")
    private String alamat;

    /*
    opsinal
    wajib @JsonIgnore : tidak menampilkan resopnse
     */
//    @JsonIgnore
//    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Barang> barang;

}

