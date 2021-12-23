package getDB.Account;

import jdbc.connect.jdbc_connector;
import table.account;
import table.covid_user;

import java.sql.*;
import java.util.ArrayList;

public class FunctionAccount {


    static public boolean CheckExisted(String username) {
        Connection conn = jdbc_connector.getConnection();
        String sql = "SELECT * FROM account_table WHERE username = ?";
        try {
            PreparedStatement PrSt = conn.prepareStatement(sql);
            PrSt.setString(1, username);
            ResultSet rs = PrSt.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    static public void AddAccount(account acc) {
        Connection conn = jdbc_connector.getConnection();
        String sql = "INSERT INTO account_table (username, pass, user_role, ban_unban)"
                + "VALUE(?, ?, ?, ? )";
        try {
            PreparedStatement PrSt = conn.prepareStatement(sql);

            PrSt.setString(1, acc.getUsername());
            PrSt.setString(2, acc.getPass());
            PrSt.setInt(3, acc.getUser_role());
            PrSt.setInt(4, acc.getBan_unban());
            PrSt.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
        }

    }

    static public void AddInfoAccount(covid_user user) {
        Connection conn = jdbc_connector.getConnection();
        String sql = "INSERT INTO covid_user (username, full_name, id, dob, house_number, address_id, patient_status, facility_id)"
                + "VALUE(?, ?, ?, ?, ?, ?, ?, ? )";
        try {
            PreparedStatement PrSt = conn.prepareStatement(sql);
            PrSt.setString(1, user.getUsername());
            PrSt.setString(2, user.getFull_name());
            PrSt.setString(3, user.getId());
            PrSt.setDate(4, user.getDob());
            PrSt.setString(5, user.getHouse_number());
            PrSt.setString(6, user.getAddress_id());
            PrSt.setString(7, user.getPatient_status());
            PrSt.setString(8, user.getFacility_id());
            PrSt.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}


