package Kontroleri;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ConnectionPool.ConnectionPool;
import Model.DAO.*;
import Model.Domen.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

public class UpdateProductController implements Initializable {

    @FXML
    private TextField brojZicaInput;
    @FXML
    private TextField konfiguracijaInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField vrstaInput;
    @FXML
    private TextField typeOfMaterialInput;
    @FXML
    private Button updateButton;
    @FXML
    private ComboBox<String> producerBox;
    @FXML
    private Button backButton;

    private Proizvod product;
    private String selectedProducer;
    private MainController invoiceController;

    public UpdateProductController(String barcode, MainController invoiceController) {
        this.product = getProductFromBarcode(barcode);
        this.invoiceController = invoiceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        insertIntoComboBox();
        fillProduct();
        if (product instanceof Gitara) {
            fillGitara();
            brojZicaInput.setDisable(false);
        } else if (product instanceof Bubanj) {
            fillBubanj();
        } else if (product instanceof Klavijatura) {
            fillKlavijatura();
            konfiguracijaInput.setDisable(false);
        }
        updateButton.setOnAction(e -> updateButtonClicked());
        producerBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedProducer = newValue;
            }
        });

        backButton.setOnAction(e -> backButtonClicked());

    }

    private Proizvod getProductFromBarcode(String barcode) {
        Proizvod proizvod = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_gitara where Barkod='" + barcode + "'");
            while (rs.next()) {
                proizvod = new Gitara(rs.getString(2),
                        rs.getString(1), rs.getString(3),
                        rs.getString(4), rs.getInt(6),
                        rs.getInt(5), rs.getInt(7), rs.getString(8));
            }

            rs = statement.executeQuery("SELECT * from prikaz_bubanj where Barkod='" + barcode + "'");
            while (rs.next()) {
                proizvod = new Bubanj(rs.getString(2),
                        rs.getString(1), rs.getString(3), rs.getString(4),
                        rs.getInt(6), rs.getInt(5),
                        rs.getString(7));
            }

            rs = statement.executeQuery("SELECT * from prikaz_klavijatura where Barkod='" + barcode + "'");
            while (rs.next()) {
                proizvod = new Klavijatura(rs.getString(2),
                        rs.getString(1), rs.getString(3), rs.getString(4),
                        rs.getInt(6), rs.getInt(5), rs.getString(7), rs.getString(8));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return proizvod;
    }

    private void insertIntoComboBox() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT JIB_P,Naziv from proizvodjac");
            while (rs.next()) {
                producerBox.getItems().add("JIB:" + rs.getString(1) + " Naziv: " + rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void fillProduct() {
        try {
            nameInput.setText(product.getNaziv());
            priceInput.setText(product.getCijena().toString());
            typeOfMaterialInput.setText(product.getVrstaMaterijala());
            String producerName = DAOProizvod.getNazivProizvodjaca(Integer.toString(product.getJib_p()));
            producerBox.getSelectionModel().select("JIB:" + product.getJib_p() + " Naziv:" + producerName);
            selectedProducer = producerBox.getSelectionModel().getSelectedItem();
        }catch(NullPointerException exception){
            exception.fillInStackTrace();
        }
    }

    private void fillGitara() {
        brojZicaInput.setText(((Gitara) product).getBrojZica().toString());
        vrstaInput.setText(((Gitara)product).getVrsta());

    }

    private void fillBubanj() {
        vrstaInput.setText(((Bubanj)product).getVrsta());
    }

    private void fillKlavijatura() {

        konfiguracijaInput.setText(((Klavijatura) product).getTipKonfiguracije());
        vrstaInput.setText(((Klavijatura)product).getVrsta());
    }

    private void updateButtonClicked() {
        if (!nameInput.getText().isEmpty() && !typeOfMaterialInput.getText().isEmpty() && !priceInput.getText().isEmpty()
                && !vrstaInput.getText().isEmpty()  && selectedProducer != null) {
            String nazivProizvoda, vrstaMaterijala;
            String cijena;
            String vrsta;
            String barkod;
            String JIB_P;

            nazivProizvoda = nameInput.getText();
            vrstaMaterijala = typeOfMaterialInput.getText();
            cijena = priceInput.getText();
            vrsta = vrstaInput.getText();
            JIB_P = selectedProducer.split(" ")[0].split(":")[1];

            String konfiguracija = null;
            Integer BrojZica = null;
            Proizvod product2 = null;

            if (product instanceof Gitara) {
                BrojZica = Integer.parseInt(brojZicaInput.getText());
                product2 = new Gitara(nazivProizvoda, product.getBarkod(),  cijena,vrstaMaterijala, Integer.parseInt(JIB_P), product.getJib_d(), BrojZica,vrsta);
                DAOGitara.updateGitara((Gitara) product2);
            } else if (product instanceof Bubanj) {
                product2 = new Bubanj(nazivProizvoda, product.getBarkod(),cijena,
                        vrstaMaterijala, Integer.parseInt(JIB_P),product.getJib_d(),vrsta);
                DAOBubanj.updateBubanj((Bubanj) product2);
            } else if (product instanceof Klavijatura) {
                konfiguracija = konfiguracijaInput.getText();
                product2 = new Klavijatura(nazivProizvoda,product.getBarkod(), cijena,
                        vrstaMaterijala, Integer.parseInt(JIB_P), product.getJib_d(),  konfiguracija, vrsta);
               DAOKlavijatura.updateKlavijatura((Klavijatura) product2);
            }



            invoiceController.updateProductInTable(product2, product);
            ((Stage) backButton.getScene().getWindow()).close();
        }
    }

    private void backButtonClicked() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

}
