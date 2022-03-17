package com.binar.grab.dao.request;

import lombok.Data;

@Data
public class TransaksiRequest {
    public Long idBarang;
    public Long idPembeli;
    public Integer qty;
    public Long id;//id transaksi
}
