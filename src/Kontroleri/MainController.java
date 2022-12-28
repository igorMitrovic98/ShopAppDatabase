package Kontroleri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ConnectionPool.ConnectionPool;
import Model.DAO.*;
import Model.Domen.*;

import java.io.IOException;
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

public class MainController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button printButton;

    @FXML
    private Label totalLabel;
    @FXML
    private ListView<Stavka> listView;
    @FXML
    private Button addButton1;
    @FXML
    private Button printButton1;
    @FXML
    private Label totalLabel1;
    @FXML
    private ListView<Stavka> listView1;
    @FXML
    private Button addProductButton2;
    @FXML
    private Button deleteProductButton2;
    @FXML
    private Button updateProductButton2;
    @FXML
    private TableView<Proizvod> productsTable2;
    @FXML
    private TableColumn<Proizvod, String> barcodeColumn;
    @FXML
    private TableColumn<Proizvod, String> nameColumn;
    @FXML
    private TableColumn<Proizvod, String> priceColumn;
    @FXML
    private TableColumn<Proizvod, String> materialColumn;
    @FXML
    private TableColumn<Proizvod, Integer> producerColumn;
    @FXML
    private TableView<Proizvodjac> producerTable3;
    @FXML
    private TableColumn<Proizvodjac, Integer> jibColumn;
    @FXML
    private TableColumn<Proizvodjac, String> producerNameColumn;
    @FXML
    private TableColumn<Proizvodjac, String> producerPhoneColumn;
    @FXML
    private TableColumn<Proizvodjac, Integer> placeColumn;
    @FXML
    private Button addProducerButton;
    @FXML
    private Button deleteProducerButton;
    @FXML
    private Tab inInvoiceTab;
    @FXML
    private Label label;
    @FXML
    private Tab outInvoiceTab;
    @FXML
    private Label label1;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab addProductTab;
    @FXML
    private Tab addProducerTab;



    private Double total = 0.0;
    private ObservableList<Stavka> listaDodatihArtikalaOBS = FXCollections.observableArrayList();
    private UlaznaFaktura ulaznaFaktura = new UlaznaFaktura(null, null,
            null, null, null, null, null, null);
    private IzlaznaFaktura izlaznaFaktura = new IzlaznaFaktura(null, null, null,
            null, null, null, null);

    private Double total1 = 0.0;
    private ObservableList<Stavka> listaDodatihArtikalaOBS1 = FXCollections.observableArrayList();

    private ObservableList<Proizvod> products = FXCollections.observableArrayList();
    private FilteredList<Proizvod> filteredList2 = new FilteredList<>(products);

    private ObservableList<Proizvodjac> producers = FXCollections.observableArrayList();
    private FilteredList<Proizvodjac> filteredList3 = new FilteredList<>(producers);

    private Proizvod selectedProduct;
    private Proizvodjac selectedProducer;
    private String organizationalUnitId;

    private ObservableList<ProizvodDostupnost> productsOBS;
    private String radnik;

    public MainController(String radnik) {
        this.radnik = radnik;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if ("TRGOVAC".equals(radnik)) {
            listView.setDisable(true);
            addButton.setDisable(true);
            printButton.setDisable(true);
            totalLabel.setDisable(true);
            label.setDisable(true);
            inInvoiceTab.setDisable(true);
            addProductTab.setDisable(true);
            addProducerTab.setDisable(true);
            tabPane.getSelectionModel().select(outInvoiceTab);
        } else if ("ADMINISTRATIVNIRADNIK".equals(radnik)) {
            listView1.setDisable(true);
            addButton1.setDisable(true);
            printButton1.setDisable(true);
            totalLabel1.setDisable(true);
            label1.setDisable(true);
            outInvoiceTab.setDisable(true);
            addProductTab.setDisable(true);
            addProducerTab.setDisable(true);
            tabPane.getSelectionModel().select(inInvoiceTab);
        } else {
            listView.setDisable(true);
            addButton.setDisable(true);
            printButton.setDisable(true);
            totalLabel.setDisable(true);
            label.setDisable(true);
            inInvoiceTab.setDisable(true);
            listView1.setDisable(true);
            addButton1.setDisable(true);
            printButton1.setDisable(true);
            totalLabel1.setDisable(true);
            label1.setDisable(true);
            outInvoiceTab.setDisable(true);
            tabPane.getSelectionModel().select(addProductTab);
        }

        addButton.setOnAction(e -> addButtonClicked());
        printButton.setOnAction(e -> printButtonClicked());
        listView.setItems(listaDodatihArtikalaOBS);

        listView.setCellFactory(lv -> new ListCell<Stavka>() {
            @Override
            public void updateItem(Stavka item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DataForInvoiceController podaci = new DataForInvoiceController(item.getNazivProizvoda(item.getBarkod()), item.getKolicina().toString(), item.getCijena());
                    AnchorPane fxmlPrikaz = podaci.getFXMLView();
                    fxmlPrikaz.prefWidthProperty().bindBidirectional(listView.prefWidthProperty());
                    setGraphic(fxmlPrikaz);
                }
            }
        });

        addButton1.setOnAction(e -> addButtonClicked1());
        printButton1.setOnAction(e -> printButtonClicked1());
        listView1.setItems(listaDodatihArtikalaOBS1);

        listView1.setCellFactory(lv -> new ListCell<Stavka>() {
            @Override
            public void updateItem(Stavka item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DataForInvoiceController podaci = new DataForInvoiceController(item.getNazivProizvoda(item.getBarkod()), item.getKolicina().toString(), item.getCijena());
                    AnchorPane fxmlPrikaz = podaci.getFXMLView();
                    fxmlPrikaz.prefWidthProperty().bindBidirectional(listView1.prefWidthProperty());
                    setGraphic(fxmlPrikaz);
                }
            }
        });

        insertIntoProductsTable();
        addProductButton2.setOnAction(e -> addProductButton2Clicked());
        deleteProductButton2.setOnAction(e -> deleteProductButton2Clicked());
        updateProductButton2.setOnAction(e -> updateProductButton2Clicked());
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barkod"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("vrstaMaterijala"));
        producerColumn.setCellValueFactory(new PropertyValueFactory<>("jib_p"));
        productsTable2.setItems(filteredList2);
        productsTable2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedProduct = newValue;
            }
        });

        producerTable3.setItems(filteredList3);
        producerTable3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedProducer = newValue;
            }
        });
        insertIntoProducersTable();
        jibColumn.setCellValueFactory(new PropertyValueFactory<>("jib_p"));
        producerNameColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        producerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("nazivMjesta"));
        addProducerButton.setOnAction(e -> addProducerButtonClicked());
        deleteProducerButton.setOnAction(e -> deleteProducerButtonClicked());

        producerTable3.setPlaceholder(new Label("Nema proizvodjaca u tabeli"));
        productsTable2.setPlaceholder(new Label("Nema proizvoda u tabeli"));

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateProducts();
                        updateProducers();
                    }
                });
            }
        }, 0, 5000);

    }

    public void updateProducts() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs;
        List<Proizvod> tmp = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM prikaz_proizvoda");
            while (rs.next()) {
                tmp.add(new Proizvod(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5),rs.getInt(6)));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception1) {
                  exception1.printStackTrace();
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

    public void updateProducers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Proizvodjac> tmp = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM prikaz_proizvodjaca");
            while (rs.next()) {
                tmp.add(new Proizvodjac(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getInt(4),rs.getString(5)));
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
        for (Proizvodjac p : tmp) {
            for (Proizvodjac pp : producers) {
                if (p.getJib_p().equals(pp.getJib_p())) {
                    b = true;
                }
            }
            if (!b) {
                producers.add(p);
                b = false;
            }
        }
    }

    private void insertIntoProducersTable() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_proizvodjaca");
            while (rs.next()) {
                producers.add(new Proizvodjac(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getInt(4),rs.getString(5)));
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
    }

    private void updateProductButton2Clicked() {
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/UpdateProduct.fxml"));
            UpdateProductController updateProductController = new UpdateProductController(selectedProduct.getBarkod(), this);
            loader.setController(updateProductController);
            Parent root = null;
            try {
                root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Izmjena proizvoda");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void addProducerButtonClicked() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/AddProducer.fxml"));
        AddProducerController addProducerController = new AddProducerController(this);
        loader.setController(addProducerController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Dodavanje proizvodjaca");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void addProductButton2Clicked() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/AddProduct.fxml"));
        AddProductController addProductController = new AddProductController(this);
        loader.setController(addProductController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Dodavanje proizvoda");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    private void deleteProducerButtonClicked() {
        if (selectedProducer != null) {
            DAOProizvodjac.obrisiProizvodjaca(selectedProducer.getJib_p());
            producers.remove(selectedProducer);
        }
    }

    private void deleteProductButton2Clicked() {
        if (selectedProduct != null) {
            DAOProizvod.obrisiProizvod(selectedProduct.getBarkod());
            products.remove(selectedProduct);
        }

    }

    private void insertIntoProductsTable() {
        products.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from prikaz_proizvoda");
            while (rs.next()) {
                products.add(new Proizvod(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5),rs.getInt(6)));
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

    }

    public void setList(UlaznaFaktura ulaznaFaktura, Double total) {
        this.ulaznaFaktura = ulaznaFaktura;
        this.total = total;
        List<Stavka> tmp = ulaznaFaktura.getStavke();
        listaDodatihArtikalaOBS.clear();
        for (Stavka s : tmp) {
            listaDodatihArtikalaOBS.add(s);
        }
        totalLabel.setText("Ukupno za platiti: " + total + "KM");
    }

    public void setList1(IzlaznaFaktura izlaznaFaktura, Double total) {
        this.izlaznaFaktura = izlaznaFaktura;
        this.total1 = total;
        List<Stavka> tmp = izlaznaFaktura.getStavke();
        listaDodatihArtikalaOBS1.clear();
        for (Stavka s : tmp) {
            listaDodatihArtikalaOBS1.add(s);
        }
        totalLabel1.setText("Ukupno za platiti: " + total + "KM");
    }

    private void printButtonClicked() {
        if (ulaznaFaktura.getStavke() != null && ulaznaFaktura.getStavke().size() > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/AddInInvoice.fxml"));
            AddInInvoiceController addInInvoice = new AddInInvoiceController(total, listView.getItems(), this);
            loader.setController(addInInvoice);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Dodavanje ulazne fakture");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            invoiceEmptyAlert();
        }
    }

    private void printButtonClicked1() {
        if (izlaznaFaktura.getStavke() != null && izlaznaFaktura.getStavke().size() > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/AddOutInvoice.fxml"));
            AddOutInvoiceController addInInvoice = new AddOutInvoiceController(total1, listView1.getItems(),
                    this, organizationalUnitId);
            loader.setController(addInInvoice);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Stampanje izlazne fakture");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            invoiceEmptyAlert();
        }
    }

    private void deleteButtonClicked() {
        Stavka stavka = listView.getSelectionModel().getSelectedItem();
        if (stavka != null) {
            listaDodatihArtikalaOBS.remove(stavka);
            ulaznaFaktura.getStavke().remove(stavka);
            total -= Integer.parseInt(stavka.getCijena());
            totalLabel.setText("Ukupno za platiti: " + total + "KM");
        }
    }

    private void deleteButtonClicked1() {
        Stavka stavka = listView1.getSelectionModel().getSelectedItem();
        if (stavka != null) {
            listaDodatihArtikalaOBS1.remove(stavka);
            izlaznaFaktura.getStavke().remove(stavka);
            total1 -= Integer.parseInt(stavka.getCijena());
            totalLabel1.setText("Ukupno za platiti: " + total + "KM");
        }
    }

    private void addButtonClicked() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/InInvoice.fxml"));
        InInvoiceController drinks = new InInvoiceController(ulaznaFaktura, total, this);
        loader.setController(drinks);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Dodavanje proizvoda za ulaznu fakturu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void addButtonClicked1() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/OutInvoice.fxml"));
        OutInvoiceController alcoholicDrinks = new OutInvoiceController(izlaznaFaktura,
                total1, this, organizationalUnitId, productsOBS);
        loader.setController(alcoholicDrinks);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Dodavanje proizvoda za izlaznu fakturu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void clearInInvoice() {
        listaDodatihArtikalaOBS.clear();
        ulaznaFaktura.setBrojRacuna(null);
        ulaznaFaktura.setDatumIzdavanja(null);
        ulaznaFaktura.setFakturaID(null);
        ulaznaFaktura.setJib_d(null);
        ulaznaFaktura.setJmbg(null);
        ulaznaFaktura.setMjestoIzdavanja(null);
        ulaznaFaktura.setOrganizacionaJedinicaID(null);
        ulaznaFaktura.setStavke(null);
        ulaznaFaktura.setUkupnaCijena(null);
        total = 0.0;
        totalLabel.setText("Ukupno za platiti: " + total + "KM");
    }

    public void clearOutInvoice() {
        listaDodatihArtikalaOBS1.clear();
        izlaznaFaktura.setDatumIzdavanja(null);
        izlaznaFaktura.setFakturaID(null);
        izlaznaFaktura.setJmbg(null);
        izlaznaFaktura.setKupacID(null);
        izlaznaFaktura.setMjestoIzdavanja(null);
        izlaznaFaktura.setOrganizacionaJedinicaID(null);
        izlaznaFaktura.setStavke(null);
        izlaznaFaktura.setUkupnaCijena(null);
        total1 = 0.0;
        totalLabel1.setText("Ukupno za platiti: " + total1 + "KM");
    }

    public void addProductInTable(Proizvod product) {
        Proizvod tmp = null;
        for (Proizvod p : products) {
            if (p.getBarkod().equals(product.getBarkod())) {
                tmp = p;
            }
        }
        if (tmp != null) {
            products.remove(tmp);
        }
        products.add(product);
    }

    public void updateProductInTable(Proizvod newProduct, Proizvod oldProduct) {
        products.remove(selectedProduct);
        products.add(newProduct);
        productsTable2.getSelectionModel().select(newProduct);
    }

    public void addProducerInTable(Proizvodjac producer) {
        Proizvodjac tmp = null;
        for (Proizvodjac p : producers) {
            if (p.getJib_p().equals(producer.getJib_p())) {
                tmp = p;
            }
        }
        if (tmp != null) {
            producers.remove(tmp);
        }
        producers.add(producer);

    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public void setObservableList(ObservableList products) {
        productsOBS = products;
    }

    private void invoiceEmptyAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);
        alert.setContentText("Nemate stavki u fakturi!!");
        alert.showAndWait();
    }

}
