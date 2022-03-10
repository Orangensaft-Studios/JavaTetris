package at.javatetris.project;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.sql.*;

/**
 * class to interact with a MariaDB database hosted on a RPI
 * @author Severin Rosner
 */
public class DataBase {


    /** url to connect to database */
    private final static String url = "jdbc:mysql://ip:3306/javatetrisdb";

    /** username to log into database */
    private final static String user = "";

    /** password to log into database */
    private final static String pass = "";

    /**
     * return if the JDBC Driver was loaded
     * @return true if loaded, false if not
     */
    public static boolean loadJDBCDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * check if DataBase can be reached
     * @return if DataBase can be reached
     */
    public static boolean checkConnection() {
        System.out.println("DataBase.java: Checking... Connecting database...");
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Database.java: Connected successfully to" + url);
            return true;
        } catch (SQLException e) {
            System.out.println("DataBase.java: " + e.getMessage());
            return false;
        }
    }

    /**
     * store Username and passwordHash to DataBase
     * @param username entered username
     * @param passwordHash password as hash
     * @return if user was created
     */
    public static String createUser(String username, String passwordHash) {

        System.out.println("DataBase.java: Connecting database to create User...");

        try {

            // start connection
            Connection connection = DriverManager.getConnection(url, user, pass);

            System.out.println("Database.java: Connected successfully to: " + url);

            PreparedStatement preparedStatement = connection.prepareStatement("select * from UserData where username = ?");
            preparedStatement.setString(1, username);
            ResultSet r1 = preparedStatement.executeQuery();

            if(r1.next()) {
                System.out.println("DataBase.java: User '" + username + "' already exists");
                return "UsrAlrExists";
            }

            String sql = "INSERT INTO UserData (username, password) VALUES (?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("DataBase.java: User '" + username + "' was created successfully!");
                return "AwC"; //Account was Created
            }
        } catch (SQLException e) {
            System.out.println("DataBase.java: " + e.getMessage());
            return "AnC"; //Account not Created
        }

        return "AnC"; //Account not Created
    }


    public static boolean onlineLogin(String username, String password) {
        System.out.println("DataBase.java: Connecting database to login to User...");

        try {
            // start connection
            Connection connection = DriverManager.getConnection(url, user, pass);

            System.out.println("Database.java: Connected successfully to: " + url);

            PreparedStatement preparedStatement = connection.prepareStatement("select username, password from UserData where username = ?");

            preparedStatement.setString(1, username);
            ResultSet r1 = preparedStatement.executeQuery();

            //if user found, look if given password matches
            if(r1.next()) {
                System.out.println("DataBase.java: User '" + username + "' exists, comparing passwords now");
                if (r1.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("DataBase.java: " + e.getMessage());
        }

        return false;


    }



}
