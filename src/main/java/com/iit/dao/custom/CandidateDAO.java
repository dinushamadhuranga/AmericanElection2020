package com.iit.dao.custom;

import com.iit.dao.CRUDDAO;
import com.iit.model.Candidate;

import java.sql.SQLException;

public interface CandidateDAO extends CRUDDAO<Candidate , Integer> {
    public int getCount() throws SQLException, ClassNotFoundException;
    public boolean increseCandidate(String party) throws SQLException, ClassNotFoundException;
}
