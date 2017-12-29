/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author samuli
 */
@Controller
public class DatabaseController {

    @RequestMapping("/database/add")
    public String add() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        c.createStatement().execute("INSERT INTO User (id, username, password) VALUES (1, 'Joni', '1234')");
        c.createStatement().execute("INSERT INTO User (id, username, password) VALUES (2, 'Matti', 'teppo')");
        c.createStatement().execute("INSERT INTO User (id, username, password) VALUES (3, 'Ted', 'president')");

        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (1, 'google', 'https://www.google.com', 'Joni', 'president')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (1, 'mooc', 'https://www.mooc.fi','Jon123', '1234')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (1, 'helsinki uni', 'https://www.helsinki.fi/en','guest', 'guest')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (1, 'yahoo', 'https://www.yahoo.com/','Johny', 'password')");

        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (2, 'google', 'https://www.google.com', 'Matti', 'president')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (2, 'mooc', 'https://www.mooc.fi','mat444', 'primeminister')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (2, 'helsinki uni', 'https://www.helsinki.fi/en','guest', 'guest')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (2, 'yahoo', 'https://www.yahoo.com/','mati', 'thursday')");

        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (3, 'google', 'https://www.google.com', 'det', 'president')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (3, 'mooc', 'https://www.imgur.fi','tet12', 'president')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (3, 'helsinki uni', 'https://www.9gag.com','guest', 'guest')");
        c.createStatement().execute("INSERT INTO LoginCredential (user_id, siteName, url, username, password) VALUES (3, 'yahoo', 'https://www.yahoo.com/','ted1234', 'password')");

        return "redirect:/form";
    }

    @RequestMapping("/database/set")
    public String set() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS User(id integer PRIMARY KEY, username varchar(50), password varchar(50))");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS LoginCredential(user_id integer, username varchar(50), password varchar(50), siteName varchar(50), url varchar(500), FOREIGN KEY(user_id) REFERENCES User(id))");

        return "redirect:/form";
    }

    @RequestMapping("/database/delete")
    public String delete() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
        c.createStatement().execute("DROP TABLE IF EXISTS User");
        c.createStatement().execute("DROP TABLE IF EXISTS LoginCredential");
        return "redirect:/form";
    }

}
