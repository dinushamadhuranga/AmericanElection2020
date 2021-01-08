package com.iit.bo.custom;

import com.iit.model.Candidate;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ManageCandidateBO {

    //return true if the candidate registration is successfully finished
    public boolean registerCandidate(Candidate candidate) throws SQLException, ClassNotFoundException;

    //return true if the relevent candidate has deleted on the database
    public boolean removeCandidate(int id) throws SQLException, ClassNotFoundException;

    // return true if the relevent candidate has updated successfully
    public boolean updateCandidate(Candidate candidate) throws SQLException, ClassNotFoundException;

    //return list of all canidates in the database
    public ObservableList<Candidate> getAllCandidates() throws SQLException, ClassNotFoundException;

    //return the number of candidates in the database;
    public int getCandidateCount() throws SQLException, ClassNotFoundException;
}
