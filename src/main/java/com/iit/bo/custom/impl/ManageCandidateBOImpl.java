package com.iit.bo.custom.impl;

import com.iit.bo.custom.ManageCandidateBO;
import com.iit.dao.custom.impl.CandidateDAOImpl;
import com.iit.model.Candidate;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCandidateBOImpl implements ManageCandidateBO{
    @Override
    public boolean registerCandidate(Candidate candidate) throws SQLException, ClassNotFoundException {
        return new CandidateDAOImpl().add(candidate);
    }

    @Override
    public boolean removeCandidate(int id) throws SQLException, ClassNotFoundException {
        return new CandidateDAOImpl().delete(id);
    }

    @Override
    public boolean updateCandidate(Candidate candidate) throws SQLException, ClassNotFoundException {
        return new CandidateDAOImpl().update(candidate);
    }

    @Override
    public ObservableList<Candidate> getAllCandidates() throws SQLException, ClassNotFoundException {
        return new CandidateDAOImpl().getAll();
    }

    @Override
    public int getCandidateCount() throws SQLException, ClassNotFoundException {
        return new CandidateDAOImpl().getCount();
    }
}
