package Kontroleri;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ConnectionPool.ConnectionPool;
import Model.Domen.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class InInvoiceController implements Initializable {

    @FXML
    private TableView<Proizvod> productsTable;
    @FXML
    private TableColumn<Proizvod, String> barcodeColumn;
    @FXML
    private TableColumn<Proizvod, String> nameColumn;
    @FXML
    private TableColumn<Proizvod, String> priceColumn;
    @FXML
    private TableColumn<Proizvod, String> materialColumn;
    @FXML
    private TableColumn<Gitara, String> vrstaGitara;
    @FXML
    private TableColumn<Bubanj, String> vrstaBubanj;
    @FXML
    private TableColumn<Klavijatura, String> vrstaKlavijatura;
    @FXML
    private TableColumn<Gitara, String> brojZicaColumn;
    @FXML
    private TableColumn<Klavijatura, String> konfiguracijaColumn;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField quantityInput;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView<Stavka> listOfItems;
    @FXML
    private Label totalLabel;
    @FXML
    private Button doneButton;

    private ObservableList<Stavka> listOfItemsOBS = FXCollections.observableArrayList();

    private ObservableList<Proizvod> products = FXCollections.observableArrayList();
    private FilteredList<Proizvod> filteredList = new FilteredList<>(products);
    private Proizvod product;
    private Double total = 0.0;
    private UlaznaFaktura ulaznaFaktura;
    private MainController invoiceController;

    public InInvoiceController(UlaznaFaktura ulaznaFaktura, Double total, MainController invoiceController) {
        this.ulaznaFaktura = ulaznaFaktura;
        this.total = total;
        this.invoiceController = invoiceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoTable();
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton.setOnAction(e -> deleteButtonClicked());
        doneButton.setOnAction(e -> doneButtonClicked());
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barkod"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("vrstaMaterijala"));
        vrstaGitara.setCellValueFactory(new PropertyValueFactory<>("vrsta"));
        vrstaBubanj.setCellValueFactory(new PropertyValueFactory<>("vrsta"));
        vrstaKlavijatura.setCellValueFactory(new PropertyValueFactory<>("vrsta"));
       brojZicaColumn.setCellValueFactory(new PropertyValueFactory<>("brojZica"));
        konfiguracijaColumn.setCellValueFactory(new PropertyValueFactory<>("tipKonfiguracije"));

        productsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                product = newValue;
            }
        });

        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getNaziv().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        listOfItems.setCellFactory(lv -> new ListCell<Stavka>() {
            @Override
            public void updateItem(Stavka item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DataForInvoiceController podaci = new DataForInvoiceController(item.getNazivProizvoda(item.getBarkod()), item.getKolicina().toString(), item.getCijena());
                    AnchorPane fxmlPrikaz = podaci.getFXMLView();
                    fxmlPrikaz.prefWidthProperty().bindBidirectional(listOfItems.prefWidthProperty());
                    setGraphic(fxmlPrikaz);
                }
            }
        });
        productsTable.setItems(filteredList);
        listOfItems.setItems(listOfItemsOBS);
        if (ulaznaFaktura.getStavke() != null) {
            for (Stavka s : ulaznaFaktura.getStavke()) {
                listOfItemsOBS.add(s);
            }
            totalLabel.setText("Ukupno za platiti: " + total + "KM");
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateProducts();

                    }
                });
            }
        }, 0, 5000);
    }

    public void updateProducts() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Proizvod> tmp = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_proizvoda");
            while (rs.next()) {
                tmp.add(new Proizvod(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getInt(6)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        boolean b = false;
        for (Proizvod p : tmp) {
            for (Proizvod pp : products) {
                if (p.getBarkod().equals(pp.getBarkod())) {
                    b = true;
                }
            }
            if (!b) {
                products.add(p);
                b = false;
            }
        }
    }

    private void doneButtonClicked() {
        ulaznaFaktura.setStavke(listOfItems.getItems());

        invoiceController.setList(ulaznaFaktura, total);
        ((Stage) doneButton.getScene().getWindow()).close();
    }

    private void deleteButtonClicked() {
        Stavka stavka = listOfItems.getSelectionModel().getSelectedItem();
        if (stavka != null) {
            listOfItemsOBS.remove(stavka);
            total -= Double.parseDouble(stavka.getCijena());
            totalLabel.setText("Ukupno za platiti: " + total + "KM");
        }
    }

    private void addButtonClicked() {
        if (!quantityInput.getText().isEmpty()) {
            Integer quantity = Integer.parseInt(quantityInput.getText());
            int tmp = -1;
            int quantityTmp = 0;
            if (quantity != null && product != null) {

                for (int i = 0; i < listOfItemsOBS.size(); i++) {
                    if (listOfItemsOBS.get(i).getBarkod().equals(product.getBarkod())) {
                        tmp = i;
                        quantityTmp = listOfItemsOBS.get(i).getKolicina();
                        break;
                    }
                }
                if (tmp == -1) {
                    listOfItemsOBS.add(new Stavka(null, product.getBarkod(), null, quantity, Double.toString(Double.parseDouble(product.getCijena()) * quantity)));
                    total += Double.parseDouble(product.getCijena()) * quantity;
                    totalLabel.setText("Ukupno za platiti: " + total + "KM");
                } else {
                    listOfItemsOBS.remove(tmp);
                    listOfItemsOBS.add(tmp, new Stavka(null, product.getBarkod(), null, quantity + quantityTmp, Double.toString(Double.parseDouble(product.getCijena()) * (quantity + quantityTmp))));
                    total += Double.parseDouble(product.getCijena()) * quantity;
                    totalLabel.setText("Ukupno za platiti: " + total + "KM");
                }
                quantityInput.clear();
            }

        }

    }

    private void insertIntoTable() {

        productsTable.getItems().clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_gitara");
            while (rs.next()) {
                products.add(new Gitara(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getString(8)));
            }

            rs = statement.executeQuery("SELECT * from prikaz_bubanj");
            while (rs.next()) {
                products.add(new Bubanj(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getInt(6),
                        rs.getString(7)));
            }

            rs = statement.executeQuery("SELECT * from prikaz_klavijatura");
            while (rs.next()) {
                products.add(new Klavijatura(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(InInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(InInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


}
