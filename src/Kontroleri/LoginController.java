package Kontroleri;

import ConnectionPool.ConnectionPool;

import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.logging.Logger;


public class LoginController implements Initializable {

    private String JMB;
    private boolean zaposleni;
    private boolean trgovac;
    private boolean magacioner;
    private boolean administrativniR;


    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField usernameInput;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginButton.setOnAction(event -> loginButtonClicked());
        loginLabel.setVisible(false);

        usernameInput.setOnKeyPressed((KeyEvent keyE) -> {
            if (keyE.getCode().equals(KeyCode.ENTER)) {
                loginButtonClicked();
            }
        });

        passwordInput.setOnKeyPressed((KeyEvent keyE) -> {
            if (keyE.getCode().equals(KeyCode.ENTER)) {
                loginButtonClicked();
            }
        });


    }

    private boolean ProvjeraLozinke(String username, String password) {

        boolean Uporedi = UporediSaBazom(username, password);
        return Uporedi;
    }

    private boolean UporediSaBazom(String username, String password) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT ZAPOSLENI.JMBG from ZAPOSLENI natural join TRGOVAC WHERE KorisnickoIme='" + username + "'and Lozinka='" + password + "'");
            while (resultSet.next()) {
                JMB = resultSet.getString(1);
            }
            if (JMB != null) {
                zaposleni = true;
                trgovac = true;
                return true;
            }

            resultSet = statement.executeQuery("SELECT ZAPOSLENI.JMBG from ZAPOSLENI natural join MAGACIONER WHERE KorisnickoIme='" + username + "'and Lozinka='" + password + "'");
            while (resultSet.next()) {
                JMB = resultSet.getString(1);
            }
            if (JMB != null) {
                zaposleni = true;
                magacioner = true;
                return true;
            }

            resultSet = statement.executeQuery("SELECT ZAPOSLENI.JMBG from ZAPOSLENI natural join ADMINISTRATIVNI_RADNIK WHERE KorisnickoIme='" + username + "'and Lozinka='" + password + "'");
            while (resultSet.next()) {
                JMB = resultSet.getString(1);
            }
            if (JMB != null) {
                zaposleni = true;
                administrativniR = true;
                return true;
            }

        } catch (SQLException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }
        return false;
    }



    private void loginButtonClicked(){

        if(usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            return;
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        boolean authentication = ProvjeraLozinke(username,password);

        if(authentication) {
            loginLabel.setVisible(false);
            System.out.println("Prijava odobrena!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/Main.fxml"));
            MainController fakturaController=null;
            if (trgovac) {
                fakturaController = new MainController("TRGOVAC");
            } else if (magacioner) {
                fakturaController = new MainController("MAGACIONER");
            } else if (administrativniR) {
                System.out.println("Ulazna Faktura!");
                fakturaController = new MainController("ADMINISTRATIVNIRADNIK");
            }
            loader.setController(fakturaController);
            Parent root;
            try {
             root = loader.load();
                Stage stage = new Stage();
                assert root != null;
                Scene scene = new Scene(root);
                stage.setTitle("Faktura");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setOnCloseRequest((WindowEvent event) -> { System.exit(0);});
                ((Stage) loginButton.getScene().getWindow()).close();
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
        loginLabel.setVisible(true);
        System.out.println("Podaci nisu validni");}}}



