package com.binar.grab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PerpustakaanDummy {
    // step 1. Mebuat model
    private Long id;
    private String judul;
    private String penerbit;
    private String penulis;
    private String editor;
    private int qty;

    public PerpustakaanDummy(Long id, String judul, String penerbit, String penulis, String editor, int qty) {
        this.id = id;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.editor = editor;
        this.qty = qty;
    }
    /*
    {
     "id":"1",
     "judul":"java buku",
     "penerbit":"erlangga",
     "penulis":"indra",
     "editor":"teguh",
     "qty":10
    }
     */

}
