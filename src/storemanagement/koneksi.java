/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hp
 */

//Koneksi Mysql//
public class koneksi {
    
    Connection conn = null;
    
    public static Connection koneksi(){
        String driver = "com.mysql.jdbc.Driver";
        String host = "jdbc:mysql://localhost/store";
        String user = "root";
        String pass = "";
        
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(host,user,pass);
            System.out.println("Koneksi Berhasil");
            return conn;
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi Gagal "+e);
        }
        
        return null;
    }
    
    //Get data user//
    public static ObservableList<user> getDatauser() {
        
        Connection conn = koneksi();
        ObservableList<user> list = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps = conn.prepareStatement("select * from user");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new user(Integer.parseInt(rs.getString("user_Id")), Integer.parseInt(rs.getString("Harga")), 
                        rs.getString("Nama_barang"), rs.getString("Kategori"), rs.getString("Berat"),
                        rs.getString("Status") ));
            }
            
        } catch (Exception e) {
            
        }
        
        return list;
    }
    
}
