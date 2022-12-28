package Kontroleri;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

import javafx.stage.WindowEvent;
import Kontroleri.LoginController;

public class ShopMuzickeOpreme extends Application{


        @Override
        public void start(Stage primaryStage) throws Exception {

            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilderConfig/Login.fxml"));
            LoginController loginController = new LoginController();
            loader.setController(loginController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Prijava!");
            primaryStage.show();
            primaryStage.setResizable(false);
        }


    public static void main(String[] args) {
        launch(args);
    }
    }
