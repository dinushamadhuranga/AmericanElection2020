package com.iit.controllers.impl;

import com.iit.bo.custom.impl.AdminHomeBOImpl;
import com.iit.controllers.Controller;
import com.iit.db.DBConnection;
import com.iit.model.Candidate;
import com.iit.model.Voter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VotingController implements Controller {

    @FXML
    private TableView<Candidate> tblCandidate;
    @FXML
    private TableColumn<Candidate, String> clmParty;
    @FXML
    private TableColumn<Candidate, String> clmName;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private TextField txtIDnumber;
    @FXML
    private Button btnSubmit;
    @FXML
    private Label lblName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblNIC;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblDob;
    @FXML
    private Label lblProvince;
    @FXML
    private Label lblCity;
    @FXML
    private Button btnConfirm;

    //execute when press on the voting button
    @FXML
    void btnConfirm_OnAction(ActionEvent event) throws SQLException {
        Candidate selectedCandidate = tblCandidate.getSelectionModel().getSelectedItem();
        if (selectedCandidate != null){
            try {
                if (new AdminHomeBOImpl().finalizeVote(selectedCandidate.getParty() , voter.getVoter_id())){
                    new Alert(Alert.AlertType.INFORMATION,"Your vote added successfully !!!!!").showAndWait();
                    resetAll();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Please select candidate !!!!!").showAndWait();
        }

    }

    //to store the voter's details
    private Voter voter;

    //executes when click on the submit button and check the validation of the user input values from the database
    @FXML
    void btnSubmit_OnAction(ActionEvent event) {
        if (!txtIDnumber.getText().isEmpty()) {
            try {
                voter = new AdminHomeBOImpl().getVoter(txtIDnumber.getText());
                System.out.println(voter.getName());
                if (!(voter.getName()== null)){
                    if (voter.getIsvoted()){
                        new Alert(Alert.AlertType.ERROR,"You have already put your vote.....try on next election").showAndWait();
                    }else{
                        lblName.setText(voter.getName());
                        lblAddress.setText(voter.getAddress());
                        lblCity.setText(voter.getCity());
                        lblProvince.setText(voter.getProvince());
                        lblDob.setText(voter.getDob().toString());
                        lblEmail.setText(voter.getEmail());
                        lblNIC.setText(voter.getNic());

                        centerPane.setDisable(false);
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Please enter registered nic number!!!!!").showAndWait();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Please enter your nic number").showAndWait();
        }

    }


    //to load candidate details to the voters voting form
    private void loadCandidates() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select party,first_name from candidate";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Candidate> candidates = FXCollections.observableArrayList();

        while (resultSet.next()){
            Candidate candidate = new Candidate();
            candidate.setFirst_name(resultSet.getString("first_name"));
            candidate.setParty(resultSet.getString("party"));
            candidates.add(candidate);
        }
        clmName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        clmParty.setCellValueFactory(new PropertyValueFactory<>("party"));

        tblCandidate.setItems(candidates);

    }

//reset all the fields and labels
    private void resetAll() {
        voter = new Voter();
        txtIDnumber.setText(null);
        centerPane.setDisable(true);
        lblNIC.setText(null);
        lblEmail.setText(null);
        lblDob.setText(null);
        lblProvince.setText(null);
        lblCity.setText(null);
        lblAddress.setText(null);
        lblName.setText(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        centerPane.setDisable(true);
        try {
            loadCandidates();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void started() {
    }
}
