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
    private final static String url = "jdbc:mysql://0.tcp.eu.ngrok.io:10120/javatetrisdb";

    /** username to log into database */
    private final static String user = "jt";

    /** password to log into database */
    private final static String pass = "fqD6P&&NT5Kj";

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

        System.out.println("DataBase.java: Connecting database...");

        try {

            // start connection
            Connection connection = DriverManager.getConnection(url, user, pass);

            System.out.println("Database.java: Connected successfully to: " + url);

            PreparedStatement st = connection.prepareStatement("select * from UserData where username = ?");
            st.setString(1, username);
            ResultSet r1 = st.executeQuery();

            if(r1.next()) {
                System.out.println("DataBase.java: User '" + username + "' already exists");
                return "Username already exists";
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

}
