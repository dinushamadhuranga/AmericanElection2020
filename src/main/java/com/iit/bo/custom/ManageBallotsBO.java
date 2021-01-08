package com.iit.bo.custom;

import com.iit.model.Voter;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageBallotsBO {

    //return true if the voter is successfully add into the database. otherwise send false
    public boolean registerVoter(Voter voter) throws SQLException, ClassNotFoundException;

    //return true if the voters are successfully add into the database
    public boolean registerVoters(ArrayList<Voter> voters) throws SQLException, ClassNotFoundException;

    //return true if the relevent voter is delete from the database
    public boolean removeVoter(String id) throws SQLException, ClassNotFoundException;

    //return true if the relevent voter's details are successfully updated into the database
    public boolean updateVoter(Voter voter) throws SQLException, ClassNotFoundException;

    //return the list of all voters in the database
    public ObservableList<Voter> getAllVoters() throws SQLException, ClassNotFoundException;

    //return number of voters in ther database
    public int getVotersCount() throws SQLException, ClassNotFoundException;
}
