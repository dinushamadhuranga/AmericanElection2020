package com.iit.dao.custom;

import com.iit.model.Admin;

import java.sql.SQLException;

public interface AdminDAO {
    public Admin getAdmin(Admin admin) throws SQLException, ClassNotFoundException;
}
