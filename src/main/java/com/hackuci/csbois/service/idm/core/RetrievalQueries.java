package com.hackuci.csbois.service.idm.core;

import com.hackuci.csbois.service.idm.IDMService;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.Param;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrievalQueries {
    public static ResultSet getUser(String email)
    {

        ResultSet rs = null;
        ServiceLogger.LOGGER.info("Building our query ...");
        String query = "    SELECT *\n" +
                       "    FROM user\n" +
                       "    WHERE email = ?\n";
        try {
            ServiceLogger.LOGGER.info("Preparing our query ...");
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ServiceLogger.LOGGER.info("Running our query ...");
            ps.setString(1, email);
            rs = ps.executeQuery();
        } catch(SQLException e){
            ServiceLogger.LOGGER.info("Something went wrong with the query:\n " + query);
            e.printStackTrace();
        }
        return rs;
    }
}
