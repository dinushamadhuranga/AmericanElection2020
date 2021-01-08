package com.iit.controllers.impl;

import com.iit.bo.custom.impl.ManageBallotsBOImpl;
import com.iit.controllers.Controller;
import com.iit.model.Voter;
import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ManageBallotsController implements Controller {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkIsStarted();
        cmbCity.getItems().addAll("colombo", "kalutara", "gampaha", "negombo");
        cmbProvince.getItems().addAll("western", "eastern", "southern", "northern");
        loadAllVoters();
        resetAll();
    }

    boolean isStarted = false;

    private void checkIsStarted() {
        if (isStarted) {
            btnCancel.setDisable(true);
            btnRegister.setDisable(true);
            btnUpdate.setDisable(true);
            btnImportFile.setDisable(true);
            btnRemove.setDisable(true);
            paneVoters.setDisable(true);
//            tblBollotsTabel.setDisable(true);
            new Alert(Alert.AlertType.INFORMATION, "Voting is already stareted you cant manage any ballot proccesses!!!").showAndWait();
        }
    }

    @FXML
    private TableView<Voter> tblBollotsTabel;
    @FXML
    private TableColumn<Voter, Integer> clmID;
    @FXML
    private TableColumn<Voter, String> clmName;
    @FXML
    private TableColumn<Voter, String> clmAddress;
    @FXML
    private TableColumn<Voter, Date> clmDOB;
    @FXML
    private TableColumn<Voter, String> clmNIC;
    @FXML
    private TableColumn<Voter, String> clmEmail;
    @FXML
    private TableColumn<Voter, String> clmProvince;
    @FXML
    private TableColumn<Voter, String> clmCity;
    @FXML
    private TableColumn<Voter, String> clmGender;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtContact;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXDatePicker dateDOB;
    @FXML
    private JFXComboBox<String> cmbProvince;
    @FXML
    private JFXComboBox<String> cmbCity;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtNic;
    @FXML
    private JFXRadioButton rdbMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton rdbFemale;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private Button btnImportFile;
    @FXML
    private Label lblNumOfBallots;
    @FXML
    private Pane paneVoters;

    // this id variable is used for store selected voter id on the table...because selected table row wii be missed if we click another button
    int voter_id;

    //to store the nic of the voter when click on the table
    String nic;


    //executes when click on the import files button
    @FXML
    void btnImportFile_OnAction(ActionEvent event) throws FileNotFoundException, SQLException {

        //to store the selected file
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            int index = fileName.lastIndexOf('.');
            String extension = fileName.substring(index + 1);

            // only valid for .txt files and the correct order.. these condtions for check that and get the details of the file
            if (extension.equals("txt")) {
                String absolutePath = selectedFile.getAbsolutePath();
                Scanner scan = new Scanner(selectedFile);
                String info = "";

                ArrayList<Voter> voters = new ArrayList<>();
                HashMap<String, String> voterdetails = new HashMap<>();
                while (scan.hasNext()) {
                    try {
                        for (int j = 0; j < 8; j++) {
                            String[] keyvalue = scan.nextLine().split(":");
                            voterdetails.put(keyvalue[0], keyvalue[1]);
                        }
                        Voter voter = new Voter();
                        voter.setName(voterdetails.get("name"));
                        voter.setAddress(voterdetails.get("address"));
                        voter.setEmail(voterdetails.get("email"));
                        voter.setCity(voterdetails.get("city"));
                        voter.setProvince(voterdetails.get("province"));
                        voter.setDob(Date.valueOf(voterdetails.get("dob")));
                        voter.setGender(voterdetails.get("gender"));
                        voter.setNic(voterdetails.get("nic"));

                        voters.add(voter);
                        voterdetails.clear();
                        if (scan.hasNext()) {
                            scan.nextLine();
                        }
                    } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                        new Alert(Alert.AlertType.ERROR, "incorrect order or data in the file").showAndWait();
                        return;
                    }
                }

                try {
                    //check the file is been added already
                    boolean isAdded = new ManageBallotsBOImpl().registerVoters(voters);
                    if (isAdded) {
                        new Alert(Alert.AlertType.INFORMATION, "Data added successfully ").showAndWait();
                        loadAllVoters();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Wrong data in the file").showAndWait();
                    }
                } catch (SQLIntegrityConstraintViolationException e){
                    new Alert(Alert.AlertType.INFORMATION, "You are already added this file").showAndWait();
                } catch (ClassNotFoundException e) {
                    System.out.println("fuck here");
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please input .txt type file").showAndWait();
            }
        }
    }

    //executes when press on the cancel button
    @FXML
    void btnCancel_OnAction(ActionEvent event) {
        resetAll();
    }

    //execute when press on the register button
    @FXML
    void btnRegister_OnAction(ActionEvent event) {
        //check the validity of the fields
        boolean allvalid = check_Candidate_Validity();
        if (allvalid) {
            //add data into the database
            try {
                boolean isRegistered = new ManageBallotsBOImpl().registerVoter(new Voter(txtName.getText(), txtAddress.getText(), Date.valueOf(dateDOB.getValue()), txtEmail.getText(), txtNic.getText(), rdbMale.isSelected() ? "male" : "female", cmbProvince.getValue(), cmbCity.getValue()));
                if (isRegistered) {
                    new Alert(Alert.AlertType.INFORMATION, "Voter successfully registered").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again!!!").showAndWait();
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Nic or contact number already using .. Try again").showAndWait();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Please check your inputs .. Try again").showAndWait();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "some thing went wrong").showAndWait();
            } finally {
                AdminMenuController.informUpdateControllerRoom();
                resetAll();
                loadAllVoters();
            }
        }
    }

    //this method is checking the validity of the user input data of all fields and specially check contact number.
    private boolean check_Candidate_Validity() {
        try {
            if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtEmail.getText().isEmpty() || txtContact.getText().isEmpty() || txtNic.getText().isEmpty() || dateDOB.getValue() == null || gender.getSelectedToggle() == null || cmbProvince.getSelectionModel().isEmpty() || cmbCity.getSelectionModel().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all text fields").showAndWait();
                return false;
            }

            if (txtContact.getText().matches("[0-9]") || txtContact.getText().length() != 10) {
                new Alert(Alert.AlertType.ERROR, "Please enter valid contact number").showAndWait();
                return false;
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.WARNING, "Please fill all text fields").showAndWait();
            return false;
        }

        return true;
    }

    //    execute when click on the remove button and delete the selected voter from the database
    @FXML
    void btnRemove_OnAction(ActionEvent event) {

        try {
            boolean isRemoved = new ManageBallotsBOImpl().removeVoter(nic);
            if (isRemoved) {
                new Alert(Alert.AlertType.INFORMATION, "Voter deleted succesfully").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please try again").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            loadAllVoters();
            resetAll();
        }
    }

    //execute on update button pressed and update the data base again with new values which is modified by the user
    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

        try {
            boolean isUpdated = new ManageBallotsBOImpl().updateVoter(new Voter(voter_id, txtName.getText(), txtAddress.getText(), Date.valueOf(dateDOB.getValue().toString()), txtEmail.getText(), txtNic.getText(), rdbMale.isSelected() ? "male" : "female", cmbProvince.getValue(), cmbCity.getValue()));
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Voter updated successfully !!!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please try again !!!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("fuck");
            e.printStackTrace();
        } finally {
            resetAll();
            loadAllVoters();
        }

    }


    /*
    this mehtod fill all tables and other components with seleced row data when table row clicking
    * */
    @FXML
    void tblBollotsTabel_OnMouseClick(MouseEvent event) {
        Voter selectedItem = tblBollotsTabel.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !isStarted) {
            voter_id = selectedItem.getVoter_id();
            nic = selectedItem.getNic();
            btnRemove.setDisable(false);
            btnUpdate.setDisable(false);
            btnRegister.setDisable(true);
            btnCancel.setDisable(false);

            txtName.setText(selectedItem.getName());
            txtNic.setText(selectedItem.getNic());
            txtEmail.setText(selectedItem.getEmail());
            txtAddress.setText(selectedItem.getAddress());
//            txtContact.setText(String.valueOf(selectedItem.getContact()));
            dateDOB.setValue(LocalDate.parse(selectedItem.getDob().toString()));
            cmbProvince.setValue(selectedItem.getProvince());
            cmbCity.setValue(selectedItem.getCity());
            if (selectedItem.getGender().equals("male")) {
                rdbMale.setSelected(true);
            } else {
                rdbFemale.setSelected(true);
            }
        }
    }

    // can reset all setting as the defalut settings by invoking this method..
    private void resetAll() {

        btnRegister.setDisable(false);
        btnUpdate.setDisable(true);
        btnRemove.setDisable(true);

        txtAddress.setText(null);
        txtNic.setText(null);
        txtContact.setText(null);
        txtEmail.setText(null);
        txtName.setText(null);
        dateDOB.setValue(null);
        cmbCity.setValue(null);
        cmbProvince.setValue(null);
        gender.selectToggle(null);


    }

    // this method is used for store the number of registered voters ..
    int registeredVoters = 0;

    // this method always load the table from get data from the database ...
    private void loadAllVoters() {

        try {
            ObservableList<Voter> allVoters = new ManageBallotsBOImpl().getAllVoters();

            clmID.setCellValueFactory(new PropertyValueFactory<>("voter_id"));
            clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
            clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            clmDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            clmNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
            clmProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            clmCity.setCellValueFactory(new PropertyValueFactory<>("city"));

            tblBollotsTabel.setItems(allVoters);
            lblNumOfBallots.setText(String.valueOf(new ManageBallotsBOImpl().getVotersCount()));
//            lblNumOfBallots.setText(String.valueOf(registeredVoters));
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
