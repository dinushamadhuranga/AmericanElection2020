package com.iit.bo.custom;

import com.iit.model.Candidate;
import com.iit.model.Voter;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface AdminHomeBO {

    //to see if it is possible to start the voting .. this will send false if no candaidate or no voters in the database
    public boolean checkVotingStart() throws SQLException, ClassNotFoundException;

    //return the hashmaps of the male,female and male + female which is including the province and relevent number of voters
    public HashMap<String , HashMap<String , Integer>> getVotersStats() throws SQLException, ClassNotFoundException;;

    //return the count of the candidates in the database
    public int getCandidateCount() throws SQLException, ClassNotFoundException;

    //return number of voters in the database
    public int getVoterCount() throws SQLException, ClassNotFoundException;

    //return number of female and male voters from the database
    public int[] getVoterCountByGender() throws SQLException, ClassNotFoundException;

    //return the all of the voter details of following nic from the database
    public Voter getVoter(String nic) throws SQLException, ClassNotFoundException;

    //return true if the vote is successfully added to the database
    public boolean finalizeVote(String party , int voter_id) throws SQLException, ClassNotFoundException;

    //return all of the details of the candiates in the database
    public ObservableList<Candidate> getFinalResults() throws SQLException, ClassNotFoundException;
}
