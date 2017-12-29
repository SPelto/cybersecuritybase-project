package sec.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
//        String username = "'asd'";
//        String password = "'12asdas34'";
//        Connection c = DriverManager.getConnection("jdbc:sqlite:database");
//        ResultSet r = c.createStatement().executeQuery("SELECT * FROM User WHERE username =" + username + "AND password = " + password);
//
//        while (r.next()) {
//            System.out.print(r.getString("username") + "\t");
//            System.out.println(r.getString("username").getClass());
//
//            System.out.print(r.getString("password") + "\t");
//            System.out.println(r.getString("password").getClass());
//
//        }

    }
}
