package com.iit.bo.custom.impl;

import com.iit.bo.custom.AdminBO;
import com.iit.dao.custom.impl.AdminDAOImpl;
import com.iit.model.Admin;

import java.sql.SQLException;

public class AdminBOImpl implements AdminBO {

    public Admin getAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        return new AdminDAOImpl().getAdmin(admin);
    }
}
