package com.hackuci.csbois.service.idm.core;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserRecords {
    public static void insertIntoUser(String email, String password, String password_salt, String phone_number, String phone_number_salt) {
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>();
        columns.put("email", Types.VARCHAR);
        columns.put("password", Types.VARCHAR);
        columns.put("password_salt", Types.VARCHAR);
        columns.put("phone_number", Types.VARCHAR);
        columns.put("phone_number_salt", Types.VARCHAR);

        ArrayList<String> values = new ArrayList<String>();
        values.add(email);
        values.add(password);
        values.add(password_salt);
        values.add(phone_number);
        values.add(phone_number_salt);

        Inserts.insertIntoTable("user", columns, values);
    }
}
