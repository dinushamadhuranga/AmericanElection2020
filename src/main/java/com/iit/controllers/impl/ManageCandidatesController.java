package com.iit.controllers.impl;

import com.iit.bo.custom.impl.ManageCandidateBOImpl;
import com.iit.controllers.Controller;
import com.iit.db.DBConnection;
import com.iit.model.Candidate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCandidatesController implements Controller {

    //    this method run when the begining
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkIsStarted();
        paneCandidate.setDisable(true);
        cmbParty.getItems().addAll("UNP", "SLNP", "JVP", "SJB", "PJP");
        cmbReligion.getItems().addAll("Buddhdism", "Islam", "Cristianity", "Hinduism", "Other");
        loadRecentCandidates();
        lblAllParties.setText(String.valueOf(allparties));
    }

    boolean isStarted = false;

    private void checkIsStarted() {
        if (isStarted){
            btnAdd.setDisable(true);
            btnCancel.setDisable(true);
            btnclear.setDisable(true);
            btnAdd.setDisable(true);
            btnsubmit.setDisable(true);
            btnCancel.setDisable(true);
            paneCandidate.setDisable(true);
            new Alert(Alert.AlertType.INFORMATION, "Voting is already stareted you cant manage any candidate!!!").showAndWait();
        }
    }

    //this variables for represent party labels
    int allparties = 5;
    int registeredparties = 0;

    @FXML
    private Pane paneCandidate;
    @FXML
    private TableView<Candidate> tblCandidates;
    @FXML
    private TableColumn<Candidate, Integer> clmID;
    @FXML
    private TableColumn<Candidate, String> clmFirstname;
    @FXML
    private TableColumn<Candidate, String> clmSecondname;
    @FXML
    private TableColumn<Candidate, Date> clmDOB;
    @FXML
    private TableColumn<Candidate, String> clmEmail;
    @FXML
    private TableColumn<Candidate, String> clmParty;
    @FXML
    private TableColumn<Candidate, String> clmReligion;
    @FXML
    private TableColumn<Candidate, String> clmGender;
    @FXML
    private TableColumn<Candidate, Integer> clmContactnumber;
    @FXML
    private JFXTextField txtfirstname;
    @FXML
    private JFXTextField txtsecondname;
    @FXML
    private JFXTextField txtemail;
    @FXML
    private TableColumn<Candidate, String> clmNIC;
    @FXML
    private DatePicker dtdateofbirth;
    @FXML
    private JFXComboBox<String> cmbParty;
    @FXML
    private JFXTextField txtnic;
    @FXML
    private JFXTextField txtcontactnumber;
    @FXML
    private JFXComboBox<String> cmbReligion;
    @FXML
    private JFXRadioButton rdbMale;
    @FXML
    private JFXRadioButton rdbFemale;
    @FXML
    private ToggleGroup Gender;
    @FXML
    private JFXButton btnclear;
    @FXML
    private JFXButton btnsubmit;
    @FXML
    private Label lblAllParties;
    @FXML
    private Label lblRegisteredparties;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton bntUpdate;


    //    this id variable is used for store candidate id ... selected table row wii be missed if we click another button
    int candidate_id = 0;

    /*
    this mehtod fill all tables and other components with seleced row data when table row clicking
    * */
    @FXML
    void tblCandidateList_OnClicked(MouseEvent event) {
        Candidate selectedItem = tblCandidates.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !isStarted) {
            btnclear.setDisable(true);
            btnsubmit.setDisable(true);
            candidate_id = selectedItem.getCandidate_ID();
            btnRemove.setDisable(false);
            bntUpdate.setDisable(false);
            btnAdd.setDisable(true);
            btnCancel.setDisable(false);
            paneCandidate.setDisable(false);
            paneCandidate.setStyle("-fx-background-color: rgba(255 ,255 ,255 ,255 ,1)");

            txtfirstname.setText(selectedItem.getFirst_name());
            txtsecondname.setText(selectedItem.getSecond_name());
            txtnic.setText(selectedItem.getNic());
            txtemail.setText(selectedItem.getEmail());
            txtcontactnumber.setText(String.valueOf(selectedItem.getContact()));
            cmbParty.setValue(selectedItem.getParty());
            cmbReligion.setValue(selectedItem.getReligion());
            dtdateofbirth.setValue(LocalDate.parse(selectedItem.getDob().toString()));
            if (selectedItem.getGender().equals("male")) {
                rdbMale.setSelected(true);
            } else {
                rdbFemale.setSelected(true);
            }
        }
    }

    //execute on update button pressed
    @FXML
    void bntUpdate_OnAction(ActionEvent event) {

        if (check_Candidate_Validity()) {

            try {
                boolean isUpdated = new ManageCandidateBOImpl().updateCandidate(new Candidate(candidate_id, txtfirstname.getText(), txtsecondname.getText(), Date.valueOf(dtdateofbirth.getValue().toString()), txtemail.getText(), txtnic.getText(), cmbReligion.getValue(), rdbMale.isSelected() ? "male" : "female", cmbParty.getValue(), Integer.parseInt(txtcontactnumber.getText())));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Candidate updated successfully !!!").showAndWait();
                    loadRecentCandidates();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again !!!").showAndWait();
                    loadRecentCandidates();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                resetAll();
                loadRecentCandidates();
            }
        }
    }

    //execute on Add button pressed
    @FXML
    void btnAdd_OnAction(ActionEvent event) {
        paneCandidate.setDisable(false);
        paneCandidate.setStyle("-fx-background-color: white");
        btnCancel.setDisable(false);
        btnAdd.setDisable(true);
    }

    //    execute on Cancel button pressed
    @FXML
    void btnCancel_OnAction(ActionEvent event) {
        resetAll();
    }

    //    execute on remove button pressed
    @FXML
    void btnRemove_OnAction(ActionEvent event) {

        if (candidate_id != 0) {
            boolean isRemoved = false;
            try {
                isRemoved = new ManageCandidateBOImpl().removeCandidate(candidate_id);
                if (isRemoved) {
                    new Alert(Alert.AlertType.INFORMATION, "Candidate deleted succesfully").showAndWait();
                    loadRecentCandidates();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again").showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //    execute on submit button pressed
    @FXML
    void btnSubmit_OnAction(ActionEvent event) {

        if (check_Candidate_Validity()) {
            try {
                boolean isAdded = new ManageCandidateBOImpl().registerCandidate(new Candidate(txtfirstname.getText(), txtsecondname.getText(), Date.valueOf(dtdateofbirth.getValue().toString()), txtemail.getText(), txtnic.getText(), cmbReligion.getValue(), rdbMale.isSelected() ? "male" : "female", cmbParty.getValue(), Integer.parseInt(txtcontactnumber.getText())));
                if (isAdded) {
                    new Alert(Alert.AlertType.INFORMATION, "Candidate Added Successfully !").showAndWait();
                    resetAll();
                    loadRecentCandidates();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Candidate registation failed !").showAndWait();
                }
            }catch (SQLIntegrityConstraintViolationException e){
                new Alert(Alert.AlertType.ERROR, "nic or contact already using another candidate... add your details please !").showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //checking the validation of the fields are empty or in the correct format
    private boolean check_Candidate_Validity() {
        try {
            if (txtfirstname.getText().isEmpty() || txtsecondname.getText().isEmpty() || txtemail.getText().isEmpty() || txtcontactnumber.getText().isEmpty() || txtnic.getText().isEmpty() || dtdateofbirth.getValue() == null || Gender.getSelectedToggle() == null || cmbReligion.getSelectionModel().isEmpty() || cmbParty.getSelectionModel().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all text fields").showAndWait();
                return false;
            }

            if (txtcontactnumber.getText().matches("[0-9]") || txtcontactnumber.getText().length() != 10) {
                new Alert(Alert.AlertType.ERROR, "Please enter valid contact number").showAndWait();
                return false;
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.WARNING, "Please fill all text fields").showAndWait();
            return false;
        }

        return true;
    }

    //reset all the fields as to the begining level
    private void resetAll() {
        clearAllFields();
        btnAdd.setDisable(false);
        paneCandidate.setStyle("-fx-background-color: rgba(255, 0, 0, 0.1);");
        paneCandidate.setDisable(true);
        btnCancel.setDisable(true);
        btnRemove.setDisable(true);
        bntUpdate.setDisable(true);
        candidate_id = 0;
        btnsubmit.setDisable(false);
        btnclear.setDisable(false);
    }

    /*
     * execute on clear button pressed
     * */
    @FXML
    void btnclear_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to clear all fields");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                clearAllFields();
            }
        });
    }

    /*
    this method clear all of the user inputs to default level
    */
    private void clearAllFields() {
        txtcontactnumber.setText(null);
        txtnic.setText(null);
        txtemail.setText(null);
        txtsecondname.setText(null);
        txtfirstname.setText(null);
//        cmbParty.setSelectionModel(null);
//        cmbReligion.setSelectionModel(null);
        Gender.selectToggle(null);
        dtdateofbirth.setValue(null);
    }

    /*
    this method load all data in candidate table to tableview
    */
    private void loadRecentCandidates() {

        try {
            ObservableList<Candidate> candidates = new ManageCandidateBOImpl().getAllCandidates();
            registeredparties = 0;

            clmID.setCellValueFactory(new PropertyValueFactory<>("candidate_ID"));
            clmFirstname.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            clmSecondname.setCellValueFactory(new PropertyValueFactory<>("second_name"));
            clmDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            clmNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
            clmReligion.setCellValueFactory(new PropertyValueFactory<>("religion"));
            clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            clmParty.setCellValueFactory(new PropertyValueFactory<>("party"));
            clmContactnumber.setCellValueFactory(new PropertyValueFactory<>("contact"));

            tblCandidates.setItems(candidates);

            int candidateCount = new ManageCandidateBOImpl().getCandidateCount();
            lblRegisteredparties.setText(String.valueOf(candidateCount));
//            lblRegisteredparties.setText(String.valueOf(registeredparties));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void update() {
    }

    @Override
    public void started() {
        isStarted = true;
    }
}
