package com.binar.grab.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "mahasiswa")
public class Mahasiswa implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nama", nullable = false, length = 45)
    private String nama;
    @Column(name = "nim", nullable = false, length = 16)
    private String nim;
    @Column(name = "alamat", columnDefinition="TEXT")
    private String alamat;
}
