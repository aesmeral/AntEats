package com.hackuci.csbois.service.idm.core;

import com.hackuci.csbois.service.idm.IDMService;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.Param;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Util {
    public static PreparedStatement prepareStatement(String query, Param[] paramList, int paramListSize) throws SQLException {
        ServiceLogger.LOGGER.info("Preparing Statement");
        PreparedStatement ps = IDMService.getCon().prepareStatement(query);
        ServiceLogger.LOGGER.info("Entering parameters");
        for(int i = 0; i < paramListSize; i++)
            ps.setObject(paramList[i].getLocation(), paramList[i].getParam(), paramList[i].getType());
        ServiceLogger.LOGGER.info("Query ready\n" + ps.toString());
        return ps;
    }
    public static String generateSwipeID(){
        Random r = new Random();
        String swipeID = "";
        String symbols = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv!@#$%&";
        for(int i = 0; i < 64; i++)
        {
            swipeID += symbols.charAt(r.nextInt(symbols.length()));
        }
        return swipeID;
    }
}
