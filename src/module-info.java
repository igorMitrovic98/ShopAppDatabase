module ShopAplikacija {

    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    opens Model.DAO;
    opens Model.Domen;

    opens Kontroleri;
}