package za.co.mie.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static DbManager instance = null;
    private Connection con = null;

    private DbManager() {
        String url = "jdbc:mysql://localhost:3306/blueskiesairline?useSSL=false";
        String user = "root";
        String password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Got a connection");
        } catch (SQLException se) {
            System.err.println("Could not connect: " + se.getMessage());
        } catch (ClassNotFoundException se) {
            System.err.println("Could not connect: " + se.getMessage());
        }
    }
    //singleton
    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }

    public boolean closeConnection() {
        boolean retVal = false;
        if (con != null) {
            try {
                con.close();
                retVal = true;
            } catch (SQLException ex) {
                System.out.println("Error closing connection: " + ex.getMessage());
            } finally {
                con = null;
            }
        }
        return retVal;
    }
}
//public class DbManager {
//
//    private Connection con = null;
//    private String url;
//    private String database;
//    private String user;
//    private String password;
//    private String driver;
//
//    public DbManager() {
//        String urll = "jdbc:mysql://localhost:3306/blueskiesairline?useSSL=false";
//        String un = "root";
//        String pw = "root";
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection(urll, un, pw);
//            System.out.println("Got a connection");
//        } catch (SQLException se) {
//            System.err.println("Could not connect: " + se.getMessage());
//        } catch (ClassNotFoundException ex) {
//            System.err.println("Could not find jdbc driver class");
//        }
//    }
//
//    public Connection getConnection() {
//        if (con == null) {
//            createConnection();
//        }
//        return con;
//    }
//
//    public boolean closeConnection() {
//        boolean retVal = false;
//        if (con != null) {
//            try {
//                con.close();
//                retVal = true;
//            } catch (SQLException ex) {
//                System.out.println("Error closing connection: " + ex.getMessage());
//            } finally {
//                con = null;
//            }
//        }
//        return retVal;
//    }
//
//    private boolean createConnection() {
//        boolean retVal = false;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Error: " + ex.getMessage());
//            return retVal;
//        }
//        System.out.println("Driver Loaded");
//        try {
//            con = DriverManager.getConnection(url + "/" + database, user, password);
//            retVal = true;
//        } catch (SQLException ex) {
//            System.out.println("Could not connect: " + ex.getMessage());
//            return retVal;
//        }
//        return retVal;
//    }
//
//    @Override
//    public String toString() {
//        return "DBManager{" + "con=" + con + ", url=" + url + ", database=" + database + ", user=" + user + ", password=" + password + ", driver=" + driver + '}';
//    }
//}

