package com.iit.controllers.impl;

import com.iit.bo.custom.impl.AdminHomeBOImpl;
import com.iit.controllers.Controller;
import com.iit.mainroom.AmericanElectionControlRoom;
import com.iit.model.Candidate;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AdminHomeController implements Controller {

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Button btnStartVoting;
    @FXML
    private Button btnEndVoting;
    @FXML
    private Label lblTotalCandidates;
    @FXML
    private Label lblTotalvoters;
    @FXML
    private Label lblfemaleVoters;
    @FXML
    private Label lblMaleVoters;
    @FXML
    private AreaChart<?, ?> chtAreaChart;
    @FXML
    private BarChart<?, ?> chtBarchart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        btnEndVoting.setDisable(true);

        loadAllDetails();
    }

    //reload all the fields and statics in the view
    private void loadAllDetails() {
        try {

            int candidateCount = new AdminHomeBOImpl().getCandidateCount();
            int voterCount = new AdminHomeBOImpl().getVoterCount();
            int[] voterCountByGender = new AdminHomeBOImpl().getVoterCountByGender();

            lblTotalCandidates.setText(String.valueOf(candidateCount));
            lblTotalvoters.setText(String.valueOf(voterCount));
            lblMaleVoters.setText(String.valueOf(voterCountByGender[0]));
            lblfemaleVoters.setText(String.valueOf(voterCountByGender[1]));

            chtAreaChart.setTitle("Stats of Voters");
            chtAreaChart.getXAxis().setLabel("Province");
            chtAreaChart.getYAxis().setLabel("Number of voters");
            chtAreaChart.getYAxis().setTickLabelGap(1.0);

            chtBarchart.setTitle("Finale results of the election");
            chtBarchart.getXAxis().setLabel("Candidates");
            chtBarchart.getYAxis().setLabel("number of votes");
            chtBarchart.getYAxis().setTickLabelGap(1.0);

            //use to areagraph
            XYChart.Series seriesall = new XYChart.Series();
            XYChart.Series seriesmale = new XYChart.Series();
            XYChart.Series seriesfemale = new XYChart.Series();

            //insert data into serieses by using get one by one
            HashMap<String, HashMap<String, Integer>> votersStats = new AdminHomeBOImpl().getVotersStats();
            for (Map.Entry<String ,HashMap<String , Integer>> hashMap : votersStats.entrySet()){
                if (hashMap.getKey().equals("gender")){
                    seriesall.setName("AllVoters");
                    for (Map.Entry<String, Integer> entry: hashMap.getValue().entrySet() ){
                        seriesall.getData().add(new XYChart.Data(entry.getKey() , entry.getValue()));
                    }
                }else if(hashMap.getKey().equals("female")){
                    seriesfemale.setName("Female voters");
                    for (Map.Entry<String, Integer> entry: hashMap.getValue().entrySet() ){
                        seriesfemale.getData().add(new XYChart.Data(entry.getKey() , entry.getValue()));
                    }
                }else if (hashMap.getKey().equals("male")){
                    seriesmale.setName("male voters");
                    for (Map.Entry<String, Integer> entry: hashMap.getValue().entrySet() ){
                        seriesmale.getData().add(new XYChart.Data(entry.getKey() , entry.getValue()));
                    }
                }
            }
            //add all loaded data into the chart
            chtAreaChart.getData().addAll(seriesall ,seriesfemale , seriesmale);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //execute when pressing the end button
    @FXML
    void btnEndVoting_OnAction(ActionEvent event) {

        //confirm the end action of the user
        new Alert(Alert.AlertType.CONFIRMATION, "Do you want to END the election2020 voting ??").showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                            try {
                                votingwindow.close();
                                btnEndVoting.setDisable(true);

                                ObservableList<Candidate> finalResults = new AdminHomeBOImpl().getFinalResults();
                                System.out.println("!!!!!!Final result!!!!!!\n");
                                for (Candidate candidate : finalResults) {
                                    System.out.println(candidate.getFirst_name() + " : " + candidate.getVotes());
                                }

                                for (Candidate candidate : finalResults){
                                    XYChart.Series series = new XYChart.Series();
                                    series.setName(candidate.getFirst_name());
                                    series.getData().add(new XYChart.Data(candidate.getFirst_name(), candidate.getVotes()));
                                    chtBarchart.getData().add(series);
                                }


                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                );

    }

    //use to store the voting window and close it when ending
    private Stage votingwindow = new Stage();

    //executes when press on the start button
    @FXML
    void btnStartVoting_OnAction(ActionEvent event) {

        try {
            if (new AdminHomeBOImpl().checkVotingStart()) {
                //confirm the action of the user
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "You can't manage after start the voting ! Do want to continue?").showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/voting.fxml"));
                    fxmlLoader.setController(AdminMenuController.getVotingController());
                    Parent root = fxmlLoader.load();
                    votingwindow.setScene(new Scene(root));
                    votingwindow.setTitle("Voter Window");
                    votingwindow.initStyle(StageStyle.UNDECORATED);
                    votingwindow.show();
                    AdminMenuController.informStartControllerRoom();

                    btnEndVoting.setDisable(false);
                    btnStartVoting.setDisable(true);
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "You can't start voting without one candidate and voter").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
