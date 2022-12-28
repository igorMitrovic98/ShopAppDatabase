
package Kontroleri;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ConnectionPool.ConnectionPool;
import Model.DAO.*;
import Model.Domen.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class AddProductController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox;
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
    private TextField barcodeInput;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<String> producerBox;
    @FXML
    private Button backButton;

    private String  productCategory;
    private String selectedProducer;
    private MainController invoiceController;

    public AddProductController(MainController invoiceController) {
        this.invoiceController = invoiceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoComboBox();
        choiceBox.getItems().addAll("Gitara", "Bubanj", "Klavijatura");
        producerBox.valueProperty().addListener((ov, t, t1) -> {
            selectedProducer = t1;
        });
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                productCategory = newValue;
                brojZicaInput.setDisable(true);
                konfiguracijaInput.setDisable(true);
                switch (newValue) {
                    case "Gitara":
                        brojZicaInput.setDisable(false);
                        break;
                    case "Bubanj":
                        break;
                    case "Klavijatura":
                        konfiguracijaInput.setDisable(false);
                        break;
                }
                addButton.setDisable(false);
            }
        });
        addButton.setOnAction(e -> addButtonClicked());
        backButton.setOnAction(e -> backButtonClicked());
    }

    private void insertIntoComboBox() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        System.out.println("Tu je");
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT JIB_P,Naziv from proizvodjac");
            while (rs.next()) {
                producerBox.getItems().add("JIB:" + rs.getString(1) + " Naziv: " + rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddProducerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddProducerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddProducerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void addButtonClicked() {
        if (!nameInput.getText().isEmpty() && !typeOfMaterialInput.getText().isEmpty() && !priceInput.getText().isEmpty()
                && !vrstaInput.getText().isEmpty() && !barcodeInput.getText().isEmpty() && selectedProducer != null) {
            if (barcodeInput.getText().length() != 8) {
                barcodeLengthAlert();
                return;
            }
            if (checkIfBarcodeExists(barcodeInput.getText())) {

                String nazivProizvoda, vrstaMaterijala;
                String cijena;
                String vrsta;
                String barkod;
                String jib_p;
                Integer BrojZica;
                String konfiguracija;

                nazivProizvoda = nameInput.getText();
                vrstaMaterijala = typeOfMaterialInput.getText();
                cijena = priceInput.getText();
                vrsta = vrstaInput.getText();
                barkod = barcodeInput.getText();
                checkIfBarcodeExists(barkod);
                jib_p = selectedProducer.split(" ")[0].split(":")[1];


                Proizvod proizvod = null;
                switch (productCategory) {
                    case "Gitara":
                        if (!brojZicaInput.getText().isEmpty()) {
                            BrojZica = Integer.parseInt(brojZicaInput.getText());
                            proizvod = new Gitara(barkod, nazivProizvoda,cijena, vrstaMaterijala,Integer.parseInt(jib_p),2,BrojZica, vrsta);
                            DAOGitara.addGitara((Gitara) proizvod);
                        }
                        break;
                    case "Bubanj":
                        if ( true) {
                            proizvod = new Bubanj(barkod,nazivProizvoda, cijena,
                                    vrstaMaterijala,Integer.parseInt(jib_p),2,vrsta);
                            DAOBubanj.addBubanj((Bubanj) proizvod);
                        }
                        break;
                    case "Klavijatura":
                        if (!konfiguracijaInput.getText().isEmpty()) {
                           konfiguracija = konfiguracijaInput.getText();
                            proizvod = new Klavijatura(barkod, nazivProizvoda,cijena, vrstaMaterijala, Integer.parseInt(jib_p), 2, konfiguracija, vrsta);
                            DAOKlavijatura.addKlavijatura((Klavijatura) proizvod);
                        }
                        break;
                }
                producerBox.getSelectionModel().clearSelection();
                nameInput.clear();
                priceInput.clear();
                vrstaInput.clear();
                typeOfMaterialInput.clear();
                brojZicaInput.clear();
                konfiguracijaInput.clear();
                barcodeInput.clear();

                invoiceController.addProductInTable(proizvod);
            } else {
                barcodeExistsAlert();
            }
        }
    }

    private boolean checkIfBarcodeExists(String barcode) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String tmp = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT Barkod from proizvod where Barkod='" + barcode+"'");
            while (rs.next()) {
                tmp = rs.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddProducerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddProducerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return tmp == null;
    }




    private void backButtonClicked() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

    private void barcodeExistsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);
        alert.setContentText("Proizvod sa datim barkodom već postoji u bazi.");

        alert.showAndWait();
    }

    private void barcodeLengthAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);
        alert.setContentText("Barkod nije odgovarajuce velicine.\nDuzina barkoda mora biti 8.");

        alert.showAndWait();
    }

}
