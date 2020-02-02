package com.hackuci.csbois.service.idm.core;

import com.hackuci.csbois.service.idm.IDMService;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrievalQueries {
    public static ResultSet getUser(String email)
    {

        ResultSet rs = null;
        ServiceLogger.LOGGER.info("Building our query ...");
        String query = "    SELECT *\n" +
                       "    FROM user\n" +
                       "    WHERE email = ?\n";
        ServiceLogger.LOGGER.info(query);
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
    public static ResultSet getUserBySwipeID(String swipe_id)
    {
        ResultSet rs= null;
        ServiceLogger.LOGGER.info("Building our query ...");
        String query = "    SELECT email\n" +
                       "    FROM user_swipe\n" +
                       "    WHERE swipe_id = ?";
        ServiceLogger.LOGGER.info(query);
        try {
            ServiceLogger.LOGGER.info("Preparing our query ... ");
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            rs = ps.executeQuery();
            ServiceLogger.LOGGER.info("Running our query ...");
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Something went wrong with the query:\n " + query);
            e.printStackTrace();
        }
        return rs;
    }
    public static ResultSet getSwipes(int limit)
    {
        ResultSet rs = null;
        ServiceLogger.LOGGER.info("Building our query ...");
        String query = "    SELECT u.email,\n" +
                       "    availability\n" +
                       "    FROM swipe AS s\n" +
                       "    INNER JOIN user_swipe AS usw ON usw.swipe_id = s.swipe_id\n" +
                       "    INNER JOIN user AS u ON u.email = usw.email\n" +
                       "    WHERE 1 = 1\n" +
                       "    LIMIT ?";
        ServiceLogger.LOGGER.info(query);
        try {
            ServiceLogger.LOGGER.info("Preparing our query ... ");
            PreparedStatement ps = IDMService.getCon().prepareStatement(query);
            ps.setInt(1,limit);
            rs = ps.executeQuery();
        } catch (SQLException e){
            ServiceLogger.LOGGER.info("Something went wrong with your query:\n" + query);
            e.printStackTrace();
        }
        return rs;
    }
}
