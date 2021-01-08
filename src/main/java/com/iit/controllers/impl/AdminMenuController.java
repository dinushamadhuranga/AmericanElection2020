package com.iit.controllers.impl;

import com.iit.controllers.Controller;
import com.iit.mainroom.AmericanElectionControlRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMenuController implements Controller {

    @FXML
    private ImageView imgUser;
    @FXML
    private Label lblUser;
    @FXML
    private Label lbllID;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblNumber;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblHeading;
    @FXML
    private Button btnBallots;
    @FXML
    private Button btnCandidates;
    @FXML
    private BorderPane borderpnMenu;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblAdminEmail;

    //these are using for store the user name and email of the current user
    private String user_name;
    private String email;

    //american election room for controll admin option views
    private static AmericanElectionControlRoom americanElectionControlRoom;

    public AdminMenuController(AmericanElectionControlRoom controlRoom ,String user_name, String email) {
        this.user_name = user_name;
        this.email = email;
        this.americanElectionControlRoom = controlRoom;
    }

    //execute when press the ballots button
    @FXML
    void btnBallots_OnAction(ActionEvent event) throws IOException {

        borderpnMenu.getChildren().remove(borderpnMenu.getCenter());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manageballots.fxml"));
        fxmlLoader.setController(americanElectionControlRoom.getController(AmericanElectionControlRoom.controllerTypes.MANAGEBALLOTSCONTROLLER));

        Parent root = fxmlLoader.load();
        borderpnMenu.setCenter(root);
        lblHeading.setText("Manage Bollot");

    }

    //execute when press the candidate button
    @FXML
    void btnCandidates_OnAction(ActionEvent event) throws IOException {

        borderpnMenu.getChildren().remove(borderpnMenu.getCenter());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/managecandidates.fxml"));
        fxmlLoader.setController(americanElectionControlRoom.getController(AmericanElectionControlRoom.controllerTypes.MANAGECANDIDATECONTROLLER));

        Parent root= fxmlLoader.load();
        borderpnMenu.setCenter(root);
        lblHeading.setText("Manage Candidate");
    }

    //execute when press the home button
    @FXML
    void btnHome_OnAction(ActionEvent event) throws IOException {
        loadHome();
    }

    //set home.fxml to the center of the border pane
    private void loadHome() throws IOException {

        borderpnMenu.getChildren().remove(borderpnMenu.getCenter());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/adminhome.fxml"));
        fxmlLoader.setController(americanElectionControlRoom.getController(AmericanElectionControlRoom.controllerTypes.HOMECONTROLLER));

        Parent root = fxmlLoader.load();
        borderpnMenu.setCenter(root);
        lblHeading.setText("Home");
    }

    //execute when pressing the logout button
    @FXML
    void btnLogOut_OnAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to logout !");

        //confirming the action of the user
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/adminlogin.fxml"));
                fxmlLoader.setController(americanElectionControlRoom.getAdmniLoginController());

                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Cant find logout").showAndWait();
                }
                Stage stage = (Stage) btnLogOut.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Login");
                stage.show();
            }
        });

    }

    @Override
    public void update() {
    }

    @Override
    public void started() {
    }

    //for update all controllers invokin
    public static void informUpdateControllerRoom(){
        americanElectionControlRoom.notifyControllersUpdate();
    }
    //for inform voting is been started to the all controllers
    public static void informStartControllerRoom(){
        americanElectionControlRoom.notifyControllersStarted();
    }

    //get voting view when press the start button
    public static Controller getVotingController(){
        return americanElectionControlRoom.getController(AmericanElectionControlRoom.controllerTypes.VOTINGCONTROLLER);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblAdminEmail.setText(email);
        lblUserName.setText(user_name);
        try {
            loadHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
