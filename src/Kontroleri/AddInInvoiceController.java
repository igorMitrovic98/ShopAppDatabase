package Kontroleri;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ConnectionPool.ConnectionPool;
import Model.Domen.*;
import Model.DAO.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

public class AddInInvoiceController implements Initializable {

    @FXML
    private TextField placeInput;
    @FXML
    private ComboBox<String> pinBox;
    @FXML
    private ComboBox<String> nameSupplierBox;
    @FXML
    private ComboBox<String> accountSupplierBox;
    @FXML
    private ComboBox<String> productStoreBox;
    @FXML
    private Button printButton;
    @FXML
    private Button backButton;


    private ArrayList<String> pinArray = new ArrayList<>();
    private ArrayList<Dobavljac> suppliers = new ArrayList<>();
    private ArrayList<String> storeId = new ArrayList<>();

    private String selectedPin;
    private String selectedSupplier;
    private String selectedAccount;
    private String selectedStoreId;

    private Double total;
    private List<Stavka> items;
    private MainController invoiceController;


    public AddInInvoiceController(Double total, List<Stavka> items, MainController invoiceController) {
        this.total = total;
        this.items = items;
        this.invoiceController = invoiceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoFields();


        printButton.setOnAction(e -> printButtonClicked());
        pinBox.valueProperty().addListener((ov, t, t1) -> {
            selectedPin = t1;
        });

        nameSupplierBox.valueProperty().addListener((ov, t, t1) -> {
            selectedSupplier = t1;
            accountSupplierBox.getItems().clear();
            accountSupplierBox.setDisable(false);
            insertIntoAccountSupplierBox();
        });

        accountSupplierBox.valueProperty().addListener((ov, t, t1) -> {
            selectedAccount = t1;
        });

        productStoreBox.valueProperty().addListener((ov, t, t1) -> {
            selectedStoreId = t1;
        });

        backButton.setOnAction(e -> backButtonClicked());
    }

    private void backButtonClicked() {
        ((Stage)backButton.getScene().getWindow()).close();
    }

    private void printButtonClicked() {
        if(!placeInput.getText().isEmpty() && selectedPin != null && selectedAccount != null && selectedStoreId != null &&
                selectedSupplier != null && total != null) {
            String place = placeInput.getText();
            UlaznaFaktura ulaznaFaktura = new UlaznaFaktura(null,Integer.parseInt(selectedSupplier.split(" ")[0]), new Date(),
                    place,Double.toString(total), selectedPin,selectedAccount,Integer.parseInt(selectedStoreId));

            int lastId = DAOUlaznaFaktura.dodajFakturu(ulaznaFaktura);
            DAOStavka.addStavke(items, lastId);
            DAOProizvodDostupnost.dodajProizvod(items, Integer.parseInt(selectedStoreId));

            items.clear();
            ((Stage)backButton.getScene().getWindow()).close();
            invoiceController.clearInInvoice();
        }
    }



    private void insertIntoAccountSupplierBox() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        System.out.println("Tu je");
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT BrojRacuna from racun_dobavljaca where racun_dobavljaca.dobavljac_jib_d='" + selectedSupplier.split(" ")[0] + "'");
            while (rs.next()) {
                accountSupplierBox.getItems().add(rs.getString(1));
            }


        } catch (SQLException ex) {
            Logger.getLogger(AddInInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddInInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    private void insertIntoFields() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        System.out.println("Tu je");
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT JMBG from prikaz_administrativnog_radnika");
            while (rs.next()) {
                pinArray.add(rs.getString(1));
            }
            rs = statement.executeQuery("SELECT JIB_D,Naziv from prikaz_dobavljaca");
            while (rs.next()) {
                suppliers.add(new Dobavljac(rs.getInt(1), rs.getString(2), null));
            }

            rs = statement.executeQuery("SELECT OrganizacionaJedinicaID from prikaz_za_rad_sa_proizvodima");
            while(rs.next()) {
                storeId.add(rs.getString(1));
            }



            for (String s : pinArray) {
                pinBox.getItems().add(s);
            }
            for (Dobavljac d : suppliers) {
                nameSupplierBox.getItems().add(d.getJIB_D() + " " + d.getNaziv());
            }
            for(String s : storeId) {
                productStoreBox.getItems().add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddInInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddInInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
