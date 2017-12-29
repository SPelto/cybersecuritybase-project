/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author samuli
 */
public class User {

    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public void saveUser() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        password = "'" + password + "'";
        username = "'" + username + "'";
        System.out.println("INSERT INTO User(username, password) VALUES (" + this.username + ", " + this.password + ")");
        c.createStatement().execute("INSERT INTO User(username, password) VALUES (" + this.username + ", " + this.password + ")");
        c.close();
    }

    public static Map<String, String> validateLogin(String u, String p) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        HashMap<String, String> map = new HashMap();
        p = "'" + p + "'";
        u = "'" + u + "'";
        try {
            ResultSet results = c.createStatement().executeQuery("SELECT * FROM User WHERE username = " + u + " AND password = " + p);
            if (results.next()) {
                System.out.println("result id = " + results.getString("id"));
                map.put("user", (String)results.getString("id"));
                map.put("message", "Login succesfull!");
                c.close();
                return map;
            } else {
                map.put("message", "Login failed!");
                c.close();
                return map;
            }

        } catch (Exception e) {
            map.put("message", "Login failed!");
            c.close();
            return map;
        }
    }
}
