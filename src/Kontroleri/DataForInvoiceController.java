package Kontroleri;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataForInvoiceController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label totalLabel;

    @FXML
    private AnchorPane itemAnchor;

    public DataForInvoiceController(String name, String quantity, String total){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/DataForInvoice.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DataForInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nameLabel.setText(name);
        quantityLabel.setText("x" + quantity);

        totalLabel.setText("ukupno: " + total + " KM");
    }

    public AnchorPane getFXMLView(){
        return itemAnchor;
    }
}
