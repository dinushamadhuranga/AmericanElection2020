package com.iit.dao.custom;

import com.iit.dao.CRUDDAO;
import com.iit.model.Voter;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface VoterDAO extends CRUDDAO<Voter , String> {
    public int getCount() throws SQLException, ClassNotFoundException;
    public int[] getCountByGender() throws SQLException, ClassNotFoundException;
    public boolean setVote(int voter_id) throws SQLException, ClassNotFoundException;;
}
