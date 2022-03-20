package at.javatetris.project;

import java.math.BigDecimal;
import java.sql.*;

/**
 * class to interact with a MariaDB database hosted on a RPI
 * @author Severin Rosner
 */
public class DataBase {

    /** url to connect to database */
    //private final static String URL = "jdbc:mysql://eporqep6b4b8ql12.chr7pe7iynqr.eu-west-1.rds.amazonaws.com/yzt9k662l77hxtvp";
    private final static String URL = "jdbc:mysql://f80b6byii2vwv8cx.chr7pe7iynqr.eu-west-1.rds.amazonaws.com:3306/d0wqm1nk4cd7grbp";
    /** username to log into database */
    //private final static String USER = "o7ns5btnilp7rnsz";
    private final static String USER = "p3bs7tr9owwcbhai";

    /** password to log into database */
    //private final static String PASS = "woihlq9ng24vh2e2";
    private final static String PASS = "gudg7axv72fc76j2";

    /**
     * return if the JDBC Driver was loaded
     * @return true if loaded, false if not
     */
    public static boolean loadJDBCDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            Main.errorAlert("ChooseModeGUI.java: Couldn't load JDBC driver");
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
            Connection connection = getConnection();
            System.out.println("Database.java: Connected successfully to" + URL);
            return true;
        } catch (SQLException e) {
            Main.errorAlert("DataBase.java");
            System.out.println("DataBase.java: " + e.getMessage());
            return false;
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
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
            Connection connection = getConnection();
            System.out.println("Database.java: Connected successfully to: " + URL);

            //looks if the user already exists in DataBase
            PreparedStatement preparedStatement = connection.prepareStatement("select * from UserData where username = ?");
            preparedStatement.setString(1, username);
            ResultSet r1 = preparedStatement.executeQuery();
            if (r1.next()) {
                System.out.println("DataBase.java: User '" + username + "' already exists");
                return "UsrAlrExists";
            }

            //create online account
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
            Main.errorAlert("DataBase.java");
            System.out.println("DataBase.java: " + e.getMessage());
            return "AnC"; //Account not Created
        }

        return "AnC"; //Account not Created
    }

    /**
     * try an online login
     * @param username to login
     * @param password entered password
     * @return error value or success
     */
    public static String onlineLogin(String username, String password) {
        System.out.println("DataBase.java: Connecting database to login to User '" + username + "'");

        try {
            // start connection
            Connection connection = getConnection();

            System.out.println("Database.java: Connected successfully to: " + URL);

            PreparedStatement preparedStatement = connection.prepareStatement("select username, password from UserData where username = ?");

            preparedStatement.setString(1, username);
            ResultSet r1 = preparedStatement.executeQuery();

            //if user found, look if given password matches
            if (r1.next()) {
                System.out.println("DataBase.java: User '" + username + "' exists, comparing passwords now");
                if (r1.getString("password").equals(password)) {
                    loadOnlineUserData(connection, username);

                    //return successful login
                    System.out.println("DataBase.java: Logged in with '" + username + "'");
                    preparedStatement.close();
                    return "loggedIn";
                } else {
                    System.out.println("Database.java: userorpasswordfalse1");
                    preparedStatement.close();
                    return "userOrPasswordFalse";
                }
            } else {
                System.out.println("Database.java: userorpasswordfalse2");
                preparedStatement.close();
                return "userOrPasswordFalse";
            }

        } catch (SQLException e) {
            System.out.println("DataBase.java: " + e.getMessage());
            return "coudlntLogIn";
        }
    }

    private static void loadOnlineUserData(Connection connection, String username) {

        try {
            String query = "SELECT * FROM UserData where username = ?";
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String dbusername = resultSet.getString("username");
                String password = resultSet.getString("password");
                BigDecimal hsClassic = resultSet.getBigDecimal("hsClassic");
                BigDecimal hsTime = resultSet.getBigDecimal("hsTime");
                BigDecimal hsInfinity = resultSet.getBigDecimal("hsInfinity");
                int gamesPlayed = resultSet.getInt("gamesPlayed");
                BigDecimal timePlayed = resultSet.getBigDecimal("timePlayed");
                System.out.println(userId + dbusername + password + hsClassic + hsTime + hsInfinity + gamesPlayed + timePlayed);
            }

            statement.close();

        } catch (SQLException e) {
            Main.errorAlert("DataBase.java");
            e.printStackTrace();
        }
    }



}
