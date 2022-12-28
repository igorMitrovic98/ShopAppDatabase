package Kontroleri;

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

public class OutInvoiceController implements Initializable {

    @FXML
    private TableView<ProizvodDostupnost> productsTable;
    @FXML
    private TableColumn<ProizvodDostupnost, Integer> barcodeColumn;
    @FXML
    private TableColumn<ProizvodDostupnost, String> nameColumn;
    @FXML
    private TableColumn<ProizvodDostupnost, String> priceColumn;
    @FXML
    private TableColumn<ProizvodDostupnost, String> materialColumn;
    @FXML
    private TableColumn<ProizvodDostupnost, Integer> quantityColumn;

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
    @FXML
    private ComboBox<String> organizationalUnitIdBox;

    private ObservableList<Stavka> listOfItemsOBS = FXCollections.observableArrayList();

    private ObservableList<ProizvodDostupnost> products = FXCollections.observableArrayList();
    private FilteredList<ProizvodDostupnost> filteredList = new FilteredList<>(products);
    private ProizvodDostupnost product;
    private Double total = 0.0;
    private IzlaznaFaktura izlaznaFaktura;
    private MainController invoiceController;
    private String selectedOrganizationalUnitId;

    public OutInvoiceController(IzlaznaFaktura izlaznaFaktura, Double total,
                                MainController invoiceController, String organizationalUnitId,
                                ObservableList<ProizvodDostupnost> productsOBS) {

        this.izlaznaFaktura = izlaznaFaktura;
        this.total = total;
        this.invoiceController = invoiceController;
        this.selectedOrganizationalUnitId = organizationalUnitId;
        if (productsOBS != null) {
            this.products = FXCollections.observableArrayList(productsOBS);
            this.filteredList = new FilteredList<>(this.products);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoComboBox();
        if (selectedOrganizationalUnitId != null) {
            organizationalUnitIdBox.getSelectionModel().select(selectedOrganizationalUnitId);
            productsTable.setDisable(false);
            listOfItems.setDisable(false);
            deleteButton.setDisable(false);
            quantityInput.setDisable(false);
            addButton.setDisable(false);
            nameInput.setDisable(false);
            totalLabel.setDisable(false);
            product = null;
            quantityInput.clear();

        }
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton.setOnAction(e -> deleteButtonClicked());
        doneButton.setOnAction(e -> doneButtonClicked());
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barkod"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("vrstaMaterijala"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("kolicina"));

        productsTable.setPlaceholder(new Label("Nema proizvoda u tabeli"));
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
                    DataForInvoiceController podaci = new DataForInvoiceController(item.getNazivProizvoda(item.getBarkod()), item.getKolicina().toString(), item.getCijena().toString());
                    AnchorPane fxmlPrikaz = podaci.getFXMLView();
                    fxmlPrikaz.prefWidthProperty().bindBidirectional(listOfItems.prefWidthProperty());
                    setGraphic(fxmlPrikaz);
                }
            }
        });
        productsTable.setItems(filteredList);
        listOfItems.setItems(listOfItemsOBS);
        if (izlaznaFaktura.getStavke() != null) {
            for (Stavka s : izlaznaFaktura.getStavke()) {
                listOfItemsOBS.add(s);
            }
            totalLabel.setText("Ukupno za platiti: " + total + "KM");
        }

        organizationalUnitIdBox.valueProperty().addListener((ov, t, t1) -> {
            product = null;
            quantityInput.clear();
            selectedOrganizationalUnitId = t1;
            izlaznaFaktura.setDatumIzdavanja(null);
            izlaznaFaktura.setFakturaID(null);
            izlaznaFaktura.setJmbg(null);
            izlaznaFaktura.setKupacID(null);
            izlaznaFaktura.setMjestoIzdavanja(null);
            izlaznaFaktura.setOrganizacionaJedinicaID(null);
            izlaznaFaktura.setStavke(null);
            izlaznaFaktura.setUkupnaCijena(null);
            total = 0.0;
            listOfItemsOBS.clear();
            totalLabel.setText("Ukupno za platiti: " + total + "KM");

            productsTable.setDisable(false);
            listOfItems.setDisable(false);
            deleteButton.setDisable(false);
            quantityInput.setDisable(false);
            addButton.setDisable(false);
            nameInput.setDisable(false);
            totalLabel.setDisable(false);
            insertIntoTable();
        });

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (selectedOrganizationalUnitId != null) {
                            updateProducts();
                        }
                    }
                });
            }
        }, 0, 5000);

    }

    public void updateProducts() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<ProizvodDostupnost> tmp = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_proizvod_dostupnost where OrganizacionaJedinicaId="
                    + Integer.parseInt(selectedOrganizationalUnitId));
            while (rs.next()) {
                tmp.add(new ProizvodDostupnost(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5),rs.getString(6)));
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
        for (ProizvodDostupnost p : tmp) {
            for (ProizvodDostupnost pp : products) {
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

    private void insertIntoComboBox() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT OrganizacionaJedinicaID from organizaciona_jedinica" + " natural join za_rad_sa_proizvodima natural join prodavnica");
            while (rs.next()) {
                organizationalUnitIdBox.getItems().add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void doneButtonClicked() {
        izlaznaFaktura.setStavke(listOfItems.getItems());

        invoiceController.setList1(izlaznaFaktura, total);
        invoiceController.setOrganizationalUnitId(selectedOrganizationalUnitId);
        invoiceController.setObservableList(products);
        ((Stage) doneButton.getScene().getWindow()).close();
    }

    private void deleteButtonClicked() {
        Stavka stavka = listOfItems.getSelectionModel().getSelectedItem();
        if (stavka != null) {
            listOfItemsOBS.remove(stavka);
            total -= Double.parseDouble(stavka.getCijena());
            totalLabel.setText("Ukupno za platiti: " + total + "KM");
            Integer position = null;
            ProizvodDostupnost tmp = null;
            for (ProizvodDostupnost p : products) {
                if (p.getBarkod().equals(stavka.getBarkod())) {
                    position = products.indexOf(p);
                    tmp = p;
                }
            }
            tmp.setKolicina(tmp.getKolicina() + stavka.getKolicina());
            products.set(position, tmp);
        }
    }

    private void addButtonClicked() {
        if (!quantityInput.getText().isEmpty()) {
            Integer quantity = Integer.parseInt(quantityInput.getText());
            if (quantity > product.getKolicina()) {
                productQuantityAlert();
                return;
            }
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
                    listOfItemsOBS.add(new Stavka(null, product.getBarkod(),null,quantity, Double.toString(Double.parseDouble(product.getCijena()) * quantity)));
                    total += Double.parseDouble(product.getCijena()) * quantity;
                    totalLabel.setText("Ukupno za platiti: " + total + "KM");
                } else {
                    listOfItemsOBS.remove(tmp);
                    listOfItemsOBS.add(tmp, new Stavka(null, product.getBarkod(), null, quantity + quantityTmp, Double.toString(Double.parseDouble(product.getCijena()) * (quantity + quantityTmp))));
                    total += Double.parseDouble(product.getCijena()) * quantity;
                    totalLabel.setText("Ukupno za platiti: " + total + "KM");
                }
                int position = products.indexOf(product);
                product.setKolicina(product.getKolicina() - quantity);
                products.set(position, product);

            }
        }

        quantityInput.clear();

    }

    private void insertIntoTable() {
        products.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_proizvod_dostupnost where OrganizacionaJedinicaId="
                    + Integer.parseInt(selectedOrganizationalUnitId));
            while (rs.next()) {
                products.add(new ProizvodDostupnost(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5),rs.getString(6)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void productQuantityAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);
        alert.setContentText("Količina koju ste unijeli je veća od količine proizvoda na raspolaganju.");
        alert.showAndWait();
    }
}
