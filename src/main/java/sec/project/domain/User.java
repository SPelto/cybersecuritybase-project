/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

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

    public void saveUser() throws SQLException, NoSuchAlgorithmException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");

        String myHashPassword = md5Hash(password);

        password = "'" + myHashPassword + "'";
        username = "'" + username + "'";
        System.out.println("INSERT INTO User(username, password) VALUES (" + this.username + ", " + this.password + ")");
        c.createStatement().execute("INSERT INTO User(username, password) VALUES (" + this.username + ", " + this.password + ")");
        c.close();
    }

    public static Map<String, String> validateLogin(String u, String p) throws SQLException, NoSuchAlgorithmException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        HashMap<String, String> map = new HashMap();

        String myHashPassword = md5Hash(p);
        p = "'" + myHashPassword + "'";
        u = "'" + u + "'";
        try {
            System.out.println("\n\tSELECT * FROM User WHERE username = " + u + " AND password = " + p);
            ResultSet results = c.createStatement().executeQuery("SELECT * FROM User WHERE username = " + u + " AND password = " + p);

            if (results.next()) {
                map.put("user", (String) results.getString("id"));
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

    public static void update(String u, String p, int id) throws SQLException, NoSuchAlgorithmException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        HashMap<String, String> map = new HashMap();
        String myHashPassword = md5Hash(p);
        p = "'" + myHashPassword + "'";
        u = "'" + u + "'";
        try {
            System.out.println("\n\tSELECT * FROM User WHERE username = " + u + " AND password = " + p);
            c.createStatement().execute("UPDATE User SET username = " + u + ", password = " + p + "WHERE id = " + id);
            c.close();

        } catch (Exception e) {
            map.put("message", "changing failed!");
            c.close();
        }
    }

    public static String md5Hash(String p) throws NoSuchAlgorithmException {
        MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.update(p.getBytes());
        byte[] digest = md.digest();
        String myHashPassword = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHashPassword;
    }
}
