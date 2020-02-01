package com.hackuci.csbois.service.idm.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailPasswordHelper {
    /*
    Returns true if email is of valid format <email>@<domain>.<extension>, where <email> is only alphanumeric
     */
    public static boolean isValidEmailFormat(String email) {
        //^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\.[a-zA-Z0-9_]+$
        String regex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+$";
        if(email.matches(regex)) return true;
        return false;
    }
    /*
    Returns true if email is of length < 50 and not null or empty.
    */
    public static boolean isValidEmailLength(String email) {
        if (email == null) return false;
        if (email.equals("")) return false;
        return email.length() <= 50;
    }

    /*
    Returns true if password is of length 7 <= length <= 16
     */
    public static boolean isValidPasswordLength (char[] password) {
        if (password.length >= 7 && password.length <= 16) return true;
        return false;
    }

    /*
    Returns true if password has valid contents:
        Must be at least 7 chars long
        Must contain at least one uppercase alpha
        Must contain at least one lowercase alpha
        Must contain at least one numeric
     */
    public static boolean isValidPasswordContents(char[] password) {
        String stringPass = String.valueOf(password);
        String regex = "^.*(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";
        if(stringPass.matches(regex)) return true;
        return false;
    }

    public static boolean userFound(String email) {
        try {
            ResultSet rs = RetrievalQueries.getUser(email);
            if (rs.next()) return true;
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
