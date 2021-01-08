package com.iit.dao.custom.impl;

import com.iit.dao.CrudUtil;
import com.iit.dao.custom.VoterDAO;
import com.iit.model.Candidate;
import com.iit.model.Voter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VoterDAOImpl implements VoterDAO {
    @Override
    public boolean add(Voter model) throws ClassNotFoundException, SQLException {
        return CrudUtil.executeUpdate("insert into voter(name , address , dob , email ,nic , gender , province , city) values(?,?,?,?,?,?,?,?)" , model.getName() , model.getAddress() , model.getDob() , model.getEmail() , model.getNic() , model.getGender() , model.getProvince() , model.getCity());
    }

    @Override
    public boolean delete(String s) throws ClassNotFoundException, SQLException {
        return CrudUtil.executeUpdate("delete from voter where nic = ?" , s);
    }

    @Override
    public boolean update(Voter model) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Update voter set name = ? , address = ? , dob = ?, email = ?, nic = ? , gender = ? , province = ? , city = ? where voter_id =?" , model.getName() , model.getAddress() , model.getDob() , model.getEmail() , model.getNic() , model.getGender() , model.getProvince() , model.getCity() , model.getVoter_id());
    }

    @Override
    public Voter search(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuary("select * from voter where nic = ?", s);
        while (resultSet.next()){
            return new Voter(resultSet.getInt("voter_id") ,resultSet.getString("name") , resultSet.getString("address") , resultSet.getDate("dob") , resultSet.getString("email") , resultSet.getString("nic") , resultSet.getString("gender") , resultSet.getString("province") , resultSet.getString("city") , resultSet.getBoolean("isvoted"));
        }
        return new Voter();
    }

    @Override
    public ObservableList<Voter> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuary("select * from voter");
        ObservableList<Voter> voterObservableList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            voterObservableList.add(new Voter(resultSet.getInt("voter_id") ,resultSet.getString("name") , resultSet.getString("address") , resultSet.getDate("dob") , resultSet.getString("email") , resultSet.getString("nic") , resultSet.getString("gender") , resultSet.getString("province") , resultSet.getString("city") , resultSet.getBoolean("isvoted")));
        }
        return voterObservableList;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuary("select count(voter_id) from voter");
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int[] getCountByGender() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuary("select sum(gender = 'male') as male ,sum(gender = 'female') as female from voter;");
        while (resultSet.next()){
            return new int[]{resultSet.getInt("male"), resultSet.getInt("female")};
        }
        return new int[]{0,0};
    }

    @Override
    public boolean setVote(int voter_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("update voter set isvoted = true where voter_id= ?" , voter_id);
    }
}

