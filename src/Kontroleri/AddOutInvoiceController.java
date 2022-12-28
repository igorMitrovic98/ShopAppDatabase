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

public class AddOutInvoiceController implements Initializable {

    @FXML
    private TextField placeInput;
    @FXML
    private ComboBox<String> pinBox;
    @FXML
    private ComboBox<String> customerIdBox;
    @FXML
    private Button printButton;
    @FXML
    private Button backButton;

    private Double total;
    private List<Stavka> items;

    private ArrayList<String> pinArray = new ArrayList<>();
    private ArrayList<Integer> customerIds = new ArrayList<>();
    private ArrayList<String> storeIds = new ArrayList<>();

    private String selectedPin;
    private String organizationalUnitId;

    private String selectedCustomerId;
    private MainController invoiceController;

    public AddOutInvoiceController(Double total, List<Stavka> items, MainController invoiceController, String organizationalUnitId) {
        this.total = total;
        this.items = items;
        this.invoiceController = invoiceController;
        this.organizationalUnitId = organizationalUnitId;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoFields();

        printButton.setOnAction(e -> printButtonClicked());

        pinBox.valueProperty().addListener((ov, t, t1) -> {
            selectedPin = t1;
        });

        customerIdBox.valueProperty().addListener((ov, t, t2) -> {
            selectedCustomerId = t2;
        });

        backButton.setOnAction(e -> backButtonClicked());

    }

    private void printButtonClicked() {
        if (!placeInput.getText().isEmpty() && selectedCustomerId != null && selectedPin != null && organizationalUnitId != null) {
            String place = placeInput.getText();
            IzlaznaFaktura izlaznaFaktura = new IzlaznaFaktura(null, Integer.parseInt(selectedCustomerId),new Date(),
                    place,Double.toString(total),selectedPin,Integer.parseInt(organizationalUnitId));

            System.out.println(Integer.parseInt(selectedCustomerId));

            int lastId = DAOIzlaznaFaktura.dodajFakturu(izlaznaFaktura);
            DAOStavka.addStavke(items, lastId);
            DAOProizvodDostupnost.reduceProizvod(items, Integer.parseInt(organizationalUnitId));

            items.clear();
            ((Stage) backButton.getScene().getWindow()).close();
            invoiceController.clearOutInvoice();
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
            rs = statement.executeQuery("SELECT KupacID from prikaz_kupca");
            while (rs.next()) {
                customerIds.add(rs.getInt(1));
            }
            rs = statement.executeQuery("SELECT JMBG from prikaz_trgovca where OrganizacionaJedinicaID=" + organizationalUnitId);
            while (rs.next()) {
                pinArray.add(rs.getString(1));
            }


            for (Integer i : customerIds) {
                customerIdBox.getItems().add(i.toString());
            }
            for (String s : pinArray) {
                pinBox.getItems().add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddOutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddOutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddOutInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void backButtonClicked() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

}
