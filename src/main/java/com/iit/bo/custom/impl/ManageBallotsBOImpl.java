package com.iit.bo.custom.impl;

import com.iit.bo.custom.ManageBallotsBO;
import com.iit.dao.custom.impl.CandidateDAOImpl;
import com.iit.dao.custom.impl.VoterDAOImpl;
import com.iit.db.DBConnection;
import com.iit.model.Voter;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageBallotsBOImpl implements ManageBallotsBO {

    @Override
    public boolean registerVoter(Voter voter) throws SQLException, ClassNotFoundException {
        return new VoterDAOImpl().add(voter);
    }

    @Override
    public boolean registerVoters(ArrayList<Voter> voters) throws SQLException, ClassNotFoundException {
        //first get the connection and avoid the auto commint
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        VoterDAOImpl voterDAO = new VoterDAOImpl();

        //add one by one voters in the arraylist and roll back when failed to add any of the voter
        for (Voter voter : voters) {

            boolean isAdded = voterDAO.add(voter);
            if (!isAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean removeVoter(String id) throws SQLException, ClassNotFoundException {
        return new VoterDAOImpl().delete(id);
    }

    @Override
    public boolean updateVoter(Voter voter) throws SQLException, ClassNotFoundException {
        return new VoterDAOImpl().update(voter);
    }

    @Override
    public ObservableList<Voter> getAllVoters() throws SQLException, ClassNotFoundException {
        return new VoterDAOImpl().getAll();
    }

    @Override
    public int getVotersCount() throws SQLException, ClassNotFoundException {
        return new VoterDAOImpl().getCount();
    }
}
