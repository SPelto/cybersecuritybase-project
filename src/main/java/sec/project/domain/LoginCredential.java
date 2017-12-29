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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samuli
 */
public class LoginCredential {

    private String password;
    private String username;
    private String siteName;
    private String url;
    private int user_id;

    public LoginCredential(String password, String username, String url, String siteName) {
        this.password = password;
        this.username = username;
        this.url = url;
        this.siteName = siteName;
    }

    public LoginCredential(String password, String username, String url, int user_id, String siteName) {
        this.password = password;
        this.username = username;
        this.url = url;
        this.siteName = siteName;
        this.user_id = user_id;

    }

    public static List<LoginCredential> getByUser(int id) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        ArrayList<LoginCredential> list = new ArrayList();
        System.out.println("SELECT lc.siteName, lc.url, lc.username, lc.password FROM User u JOIN LoginCredential lc ON lc.user_id = " + id);
        ResultSet r = c.createStatement().executeQuery("SELECT lc.siteName, lc.url, lc.username, lc.password FROM User u INNER JOIN LoginCredential lc ON lc.user_id = u.id WHERE u.id = " + id);

        while (r.next()) {
            String u = r.getString("username");
            String p = r.getString("password");
            String ur = r.getString("url");
            String sn = r.getString("siteName");
            System.out.println(u + "\t" + p + "\t" + ur + "\t" + sn + "\t");

            LoginCredential lc = new LoginCredential(p, u, ur, sn);
            list.add(lc);
        }

        c.close();
        return list;
    }
        public static List<LoginCredential> getBySite(int id, String sName) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        ArrayList<LoginCredential> list = new ArrayList();
        System.out.println("SELECT lc.siteName, lc.url, lc.username, lc.password "
                + "FROM User u"
                + " INNER JOIN LoginCredential lc"
                + " ON lc.user_id = u.id" 
                + " WHERE u.id = " + id 
                + " AND lc.siteName LIKE %" + sName  + "%");
        ResultSet r = c.createStatement().executeQuery("SELECT lc.siteName, lc.url, lc.username, lc.password "
                + "FROM User u"
                + " INNER JOIN LoginCredential lc"
                + " ON lc.user_id = u.id" 
                + " WHERE u.id = " + id 
                + " AND lc.siteName LIKE '%" + sName  + "%'");

        while (r.next()) {
            String u = r.getString("username");
            String p = r.getString("password");
            String ur = r.getString("url");
            String sn = r.getString("siteName");
            System.out.println(u + "\t" + p + "\t" + ur + "\t" + sn + "\t");

            LoginCredential lc = new LoginCredential(p, u, ur, sn);
            list.add(lc);
        }

        c.close();
        return list;
    }
    

    public void save() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        password = "'" + password + "'";
        username = "'" + username + "'";
        siteName = "'" + siteName + "'";
        url = "'" + url + "'";
        System.out.println("INSERT INTO LoginCredential(user_id ,username, password, siteName, url) "
                + "VALUES (" + this.user_id + ", " + this.username + ", " + this.password + ", " + this.siteName + ", " + this.url + ")");
        c.createStatement().execute("INSERT INTO LoginCredential(user_id ,username, password, siteName, url) "
                + "VALUES (" + this.user_id + ", " + this.username + ", " + this.password + ", " + this.siteName + ", " + this.url + ")");
        c.close();
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }
    

}
