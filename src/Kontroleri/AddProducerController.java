package Kontroleri;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ConnectionPool.ConnectionPool;
import Model.DAO.DAOProizvodjac;
import Model.Domen.Proizvodjac;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class AddProducerController implements Initializable {

    @FXML
    private ComboBox<String> placeBox;
    @FXML
    private TextField uinInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    private String selectedPlace;
    private MainController invoiceController;

    public AddProducerController(MainController invoiceController) {
        this.invoiceController = invoiceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertIntoComboBox();
        addButton.setOnAction(e -> addButtonClicked());
        backButton.setOnAction(e -> backButtonClicked());
        placeBox.valueProperty().addListener((ov, t, t1) -> {
            selectedPlace = t1;
        });
        setFieldForPhoneOnly(phoneInput);

    }

    private void setFieldForPhoneOnly(TextField input) {
        Pattern validDoubleText = Pattern.compile("[+]?[0-9]{0,20}");

        TextFormatter<String> textFormatter = new TextFormatter<String>(null , null,
                change -> {
                    String newText = change.getControlNewText();
                    if (validDoubleText.matcher(newText).matches()) {
                        return change;
                    } else {
                        return null;
                    }
                });

        input.setTextFormatter(textFormatter);

    }

    private void addButtonClicked() {
        if (!uinInput.getText().isEmpty() && placeBox.getSelectionModel().getSelectedItem() != null
                && !nameInput.getText().isEmpty() && !phoneInput.getText().isEmpty()) {

            Integer jib_p;
            String naziv;
            String telefon;
            Integer mjestoId;
            String nazivMjesta;

            jib_p = Integer.parseInt(uinInput.getText());
            naziv = nameInput.getText();
            telefon = phoneInput.getText();
            mjestoId = Integer.parseInt(selectedPlace.split(" ")[0].split(":")[1]);
            nazivMjesta = selectedPlace.split(" ")[1].split(":")[1];

            Proizvodjac proizvodjac = new Proizvodjac(jib_p, naziv, telefon, mjestoId,nazivMjesta);
            DAOProizvodjac.dodajProizvodjaca(proizvodjac);

            placeBox.getSelectionModel().clearSelection();
            uinInput.clear();
            nameInput.clear();
            phoneInput.clear();


            invoiceController.addProducerInTable(proizvodjac);

        } else {
            fieldsAreEmptyAlert();
        }
    }
    private void uinLengthAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);


        alert.showAndWait();
    }

    private void fieldsAreEmptyAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GRESKA");
        alert.setHeaderText(null);
        alert.setContentText("Niste unijeli sva polja!!");

        alert.showAndWait();
    }

    private void insertIntoComboBox() {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT MjestoID,Naziv from mjesto");
            while (rs.next()) {
                placeBox.getItems().add("MjestoId:" + rs.getString(1) + " Naziv:" + rs.getString(2));
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
    }

    private void backButtonClicked() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

}
