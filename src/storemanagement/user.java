/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagement;

/**
 *
 * @author hp
 */
public class user {
    
    int user_Id, Harga;
    String Nama_barang, Kategori, Berat, Status;

    public user(int user_Id, int Harga, String Nama_barang, String Kategori, String Berat, String Status) {
        this.user_Id = user_Id;
        this.Harga = Harga;
        this.Nama_barang = Nama_barang;
        this.Kategori = Kategori;
        this.Berat = Berat;
        this.Status = Status;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public int getHarga() {
        return Harga;
    }

    public String getNama_barang() {
        return Nama_barang;
    }

    public String getKategori() {
        return Kategori;
    }

    public String getBerat() {
        return Berat;
    }

    public String getStatus() {
        return Status;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public void setHarga(int Harga) {
        this.Harga = Harga;
    }

    public void setNama_barang(String Nama_barang) {
        this.Nama_barang = Nama_barang;
    }

    public void setKategori(String Kategori) {
        this.Kategori = Kategori;
    }

    public void setBerat(String Berat) {
        this.Berat = Berat;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}
