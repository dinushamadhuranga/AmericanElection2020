package com.iit.controllers;

import com.iit.bo.custom.impl.AdminBOImpl;
import com.iit.controllers.impl.*;
import com.iit.mainroom.AmericanElectionControlRoom;
import com.iit.model.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLoginController{

    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;
    @FXML
    private Button btnloggin;

    private AmericanElectionControlRoom americanElectionControlRoom;

    public AdminLoginController(AmericanElectionControlRoom americanElectionControlRoom) throws IOException {
        //set the adminlogin
        americanElectionControlRoom.setAdminLoginController(this);
        //set the controller room which is sending from the start method
        this.americanElectionControlRoom = americanElectionControlRoom;
        //add all types of controllers to controllerroom
        americanElectionControlRoom.addController(AmericanElectionControlRoom.controllerTypes.HOMECONTROLLER , new AdminHomeController());
        americanElectionControlRoom.addController(AmericanElectionControlRoom.controllerTypes.MANAGEBALLOTSCONTROLLER , new ManageBallotsController());
        americanElectionControlRoom.addController(AmericanElectionControlRoom.controllerTypes.MANAGECANDIDATECONTROLLER , new ManageCandidatesController());
        americanElectionControlRoom.addController(AmericanElectionControlRoom.controllerTypes.VOTINGCONTROLLER , new VotingController());
    }


    @FXML
    void btnloggin_On_Action(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        //get relevent admin by username and password from database
        Admin admin = new AdminBOImpl().getAdmin(new Admin(txtusername.getText(), txtpassword.getText()));

        //check the fields are empty and load the admin menu after check the database
        if (!txtusername.getText().isEmpty() && !txtpassword.getText().isEmpty()){
            if (admin.getUser_name() != null){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/adminmenu.fxml"));
                //getting the contrller from controllerroom
                AdminMenuController adminMenuController = new AdminMenuController(americanElectionControlRoom ,admin.getUser_name() , admin.getEmail());
                loader.setController(adminMenuController);

                Parent root = loader.load();
                Stage stage = (Stage) btnloggin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.centerOnScreen();
                stage.setTitle("Admin Menu");
                stage.show();
            }else{
                new Alert(Alert.AlertType.ERROR , "User name or password incorrect").showAndWait();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION , "Please input user name and password").showAndWait();
        }
    }

}
