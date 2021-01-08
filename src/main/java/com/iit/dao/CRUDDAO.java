package com.iit.dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDDAO<T , ID> extends SuperDAO{

    //to use handle create , delete , update , read and search queries and send the results

    public boolean add(T model) throws ClassNotFoundException, SQLException;
    public boolean delete(ID id) throws ClassNotFoundException, SQLException;
    public boolean update(T model) throws SQLException, ClassNotFoundException ;
    public T search(ID id) throws SQLException, ClassNotFoundException;
    public ObservableList<T> getAll() throws SQLException, ClassNotFoundException;
}
