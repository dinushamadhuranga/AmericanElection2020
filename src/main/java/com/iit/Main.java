package com.iit;

import com.iit.controllers.AdminLoginController;
import com.iit.controllers.impl.AdminHomeController;
import com.iit.mainroom.AmericanElectionControlRoom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/adminlogin.fxml"));

        // creating controller room and send it to adminlogincotroller
        loader.setController(new AdminLoginController(new AmericanElectionControlRoom()));

        Parent root = loader.load();
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
