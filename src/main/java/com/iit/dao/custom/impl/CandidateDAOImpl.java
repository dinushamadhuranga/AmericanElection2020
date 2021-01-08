package com.iit.dao.custom.impl;

import com.iit.dao.CrudUtil;
import com.iit.dao.custom.CandidateDAO;
import com.iit.model.Candidate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateDAOImpl implements CandidateDAO {
    public boolean add(Candidate model) throws ClassNotFoundException, SQLException {
        return CrudUtil.executeUpdate("insert into candidate (first_name , second_name , dob , email , nic ,religion , gender , party ,contact) values (?,?,?,?,?,?,?,?,?)", model.getFirst_name(), model.getSecond_name(), model.getDob(), model.getEmail(), model.getNic(), model.getReligion(), model.getGender(), model.getParty(), model.getContact());
    }

    public boolean delete(Integer id) throws ClassNotFoundException, SQLException {
        return CrudUtil.executeUpdate("Delete from candidate where candidate_id = ?", id);
    }

    public boolean update(Candidate model) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("update candidate set first_name = ? , second_name = ? , dob = ?, email = ?, nic = ? ,religion = ? , gender = ? , party = ? ,contact = ? where candidate_id =?", model.getFirst_name(), model.getSecond_name(), model.getDob(), model.getEmail(), model.getNic(), model.getReligion(), model.getGender(), model.getParty(), model.getContact() , model.getCandidate_ID());
    }

    public Candidate search(Integer nic) throws SQLException, ClassNotFoundException {

        ResultSet executeQuery = CrudUtil.executeQuary( "select * from candidate where nic = ?", nic);

        //get the results and return new Candidate with the details
        while (executeQuery.next()) {
            return new Candidate(executeQuery.getInt("candidate_id") , executeQuery.getString("first_name") , executeQuery.getString("second_name") , executeQuery.getDate("dob") , executeQuery.getString("email"), executeQuery.getString("nic"), executeQuery.getString("religion") , executeQuery.getString("gender") , executeQuery.getString("party") ,executeQuery.getInt("votes") , executeQuery.getInt("contact"));
        }
        return new Candidate();
    }

    public ObservableList<Candidate> getAll() throws SQLException, ClassNotFoundException {
        ResultSet executeQuery = CrudUtil.executeQuary("select * from candidate order by votes desc");
        ObservableList<Candidate> candidateObservableList = FXCollections.observableArrayList();

        //get the result set and add all of the candidates into the observablelist
        while (executeQuery.next()) {
            candidateObservableList.add(new Candidate(executeQuery.getInt("candidate_id") , executeQuery.getString("first_name") , executeQuery.getString("second_name") , executeQuery.getDate("dob") , executeQuery.getString("email"), executeQuery.getString("nic"), executeQuery.getString("religion") , executeQuery.getString("gender") , executeQuery.getString("party") ,executeQuery.getInt("votes") , executeQuery.getInt("contact")));
        }
        return candidateObservableList;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuary("select count(candidate_id) from candidate");

        //get count in the result set
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean increseCandidate(String party) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("update candidate set votes = votes +1 where party = ?" , party);
    }
}
