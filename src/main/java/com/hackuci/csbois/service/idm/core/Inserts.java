package com.hackuci.csbois.service.idm.core;


import com.hackuci.csbois.service.idm.IDMService;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.Param;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Inserts {
    private static String buildInsert(String table, ArrayList<String> columns) {
        String INSERT = "INSERT INTO " + table;
        String COLUMNSSTRING = " (";
        String VALUES = " VALUES (";

        for(int i = 0; i < columns.size(); i++) {
            if(i != 0) {
                COLUMNSSTRING += ", " + columns.get(i);
                VALUES += ", ?";
            }
            else {
                COLUMNSSTRING += columns.get(i);
                VALUES += "?";
            }
        }
        COLUMNSSTRING += ")";
        VALUES += ")";

        return INSERT + COLUMNSSTRING + VALUES;
    }

    public static void insertIntoTable(String table, LinkedHashMap<String, Integer> columns, ArrayList<String> values) {
        try {
            ArrayList<String> columnNames = new ArrayList<String>(columns.keySet());
            String insertStatement = buildInsert(table, columnNames);

            Param[] params = new Param[values.size()];
            for(int i = 0; i < values.size(); i++) {
                params[i] = Param.create(columns.get(columnNames.get(i)), values.get(i), i+1);
            }

            PreparedStatement ps = Util.prepareStatement(insertStatement, params, params.length);

            ServiceLogger.LOGGER.info("Trying insert: " + ps.toString());
            ps.executeUpdate();
            ServiceLogger.LOGGER.info("Insert succeeded.");
        }
        catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Insert failed: Unable to insert new user record.");
            e.printStackTrace();
        }
    }
}
