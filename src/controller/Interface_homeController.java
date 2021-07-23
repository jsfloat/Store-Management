/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import storemanagement.koneksi;
import storemanagement.user;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class Interface_homeController implements Initializable {
    Connection conn = koneksi.koneksi();
    PreparedStatement pst;
    
    @FXML
    private TextField txtSearch;
    
    @FXML
    private TextField txtuser_Id;
    @FXML
    private TextField txtNama_barang;
    @FXML
    private TextField txtHarga;
    @FXML
    private TextField txtBerat;
    
    @FXML
    private ComboBox<String> comboKategori;
    ObservableList<String> listK = FXCollections.observableArrayList("Elektronik", "Baju", "Celana");
    
    @FXML
    private ComboBox<String> comboStatus;
    ObservableList<String> listS = FXCollections.observableArrayList("IN STORE", "OUT STORE", "EXPIRED");
    
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnRest;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    
    @FXML
    private TableView<user> table_Store;
    @FXML
    private TableColumn<user, Integer> col_Id;
    @FXML
    private TableColumn<user, String> col_Namabarang;
    @FXML
    private TableColumn<user, Integer> col_Harga;
    @FXML
    private TableColumn<user, String> col_Kategori;
    @FXML
    private TableColumn<user, String> col_Berat;
    @FXML
    private TableColumn<user, String> col_Status;
    
    ObservableList<user> listM;
    ObservableList<user> dataList;
    int index = -1;
    ResultSet rs = null;

    
    //Method Search//
    @FXML
    void search_user() {
        col_Id.setCellValueFactory(new PropertyValueFactory<user,Integer>("user_Id"));
        col_Namabarang.setCellValueFactory(new PropertyValueFactory<user,String>("Nama_barang"));
        col_Harga.setCellValueFactory(new PropertyValueFactory<user,Integer>("Harga"));
        col_Kategori.setCellValueFactory(new PropertyValueFactory<user,String>("Kategori"));
        col_Berat.setCellValueFactory(new PropertyValueFactory<user,String>("Berat"));
        col_Status.setCellValueFactory(new PropertyValueFactory<user,String>("Status"));
        
        dataList = koneksi.getDatauser();
        table_Store.setItems(dataList);
        FilteredList<user> filteredData = new FilteredList<>(dataList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getNama_barang().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (person.getKategori().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                
                else 
                    return false;
            });
        });
        SortedList<user> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_Store.comparatorProperty());
        table_Store.setItems(sortedData);
        
    }
    
    //Method Select User//
    @FXML
    void getSelected(MouseEvent event) {
        index = table_Store.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            
            return;
        }
        txtuser_Id.setText(col_Id.getCellData(index).toString());
        txtNama_barang.setText(col_Namabarang.getCellData(index).toString());
        txtHarga.setText(col_Harga.getCellData(index).toString());
        comboKategori.setValue(col_Kategori.getCellData(index).toString());
        txtBerat.setText(col_Berat.getCellData(index).toString());
        comboStatus.setValue(col_Status.getCellData(index).toString());
        
    }
    
    //Method Refresh Tableview//  
    public void UpdateTable() {
        col_Id.setCellValueFactory(new PropertyValueFactory<user,Integer>("user_Id"));
        col_Namabarang.setCellValueFactory(new PropertyValueFactory<user,String>("Nama_barang"));
        col_Harga.setCellValueFactory(new PropertyValueFactory<user,Integer>("Harga"));
        col_Kategori.setCellValueFactory(new PropertyValueFactory<user,String>("Kategori"));
        col_Berat.setCellValueFactory(new PropertyValueFactory<user,String>("Berat"));
        col_Status.setCellValueFactory(new PropertyValueFactory<user,String>("Status"));
        
        listM = koneksi.getDatauser();
        table_Store.setItems(listM);
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        comboKategori.setItems(listK);
        comboStatus.setItems(listS);
        UpdateTable();
        search_user();
    }    

    //Button Simpan//
    @FXML
    private void btnSimpan_Click(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apakah anda akan menyimpan data");
        alert.setHeaderText(null);
        alert.setContentText("Tekan OK untuk menyimpan data, Cencel untuk batal.");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            try {
                String query = "INSERT INTO `user`(`user_Id`, `Nama_barang`, `Harga`, `Kategori`, `Berat`, `Status`) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(query);
                pst.setString(1, txtuser_Id.getText());
                pst.setString(2, txtNama_barang.getText());
                pst.setString(3, txtHarga.getText());
                pst.setString(4, comboKategori.getValue());
                pst.setString(5, txtBerat.getText());
                pst.setString(6, comboStatus.getValue());
                pst.execute();
                UpdateTable();
                search_user();

                Alert berhasil = new Alert(Alert.AlertType.INFORMATION);
                berhasil.setTitle("Informasi Database");
                berhasil.setHeaderText(null);
                berhasil.setContentText("Data "+txtNama_barang.getText()+" Telah Berhasil Disimpan\n\nTerima Kasih.");   
                berhasil.showAndWait();
                pst.close();
                
            } catch (SQLException e) {
                Alert gagal = new Alert(Alert.AlertType.ERROR);
                gagal.setTitle("Informasi Database");
                gagal.setHeaderText(null);
                gagal.setContentText("Data "+txtNama_barang.getText()+" Tidak Dapat Disimpan\n\nSilahkan Di Ulang.\n"
                        + "Error = "+e);
                gagal.showAndWait();
            }
        } else {
            alert.close();
        }
    }

    //Button Reset//
    @FXML
    private void btnReset_Click(MouseEvent event) {
        
        txtuser_Id.setText("");
        txtNama_barang.setText("");
        txtHarga.setText("");
        comboKategori.setValue("Select");
        txtBerat.setText("");
        comboStatus.setValue("Select");

    }

    //Button Update//
    @FXML
    private void btnUpdate_Click(MouseEvent event) {
        
            try {
            conn = koneksi.koneksi();
            String value1 = txtuser_Id.getText();
            String value2 = txtNama_barang.getText();
            String value3 = txtHarga.getText();
            String value4 = comboKategori.getValue();
            String value5 = txtBerat.getText();
            String value6 = comboStatus.getValue();
            
            String sql = "update user set user_Id= '"+value1+"',Nama_barang= '"+value2+"',"
                    + "Harga= '"+value3+"',Kategori= '"+value4+"',Berat= '"+value5+"',"
                    + "Status= '"+value6+"' where user_Id= '"+value1+"' ";
            
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update Success");  
            UpdateTable();
            search_user();
            
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }
    }

    
    //Buttom Delete//
    @FXML
    private void btnDelete_Click(MouseEvent event) {
        
        conn = koneksi.koneksi();
        String sql = "delete from user where user_Id = ?";
            
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtuser_Id.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "delete");
                UpdateTable();
                search_user();
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
     }
    }
    
}
