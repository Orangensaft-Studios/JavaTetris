package at.javatetris.project;

import io.github.cdimascio.dotenv.Dotenv;
import java.math.BigDecimal;
import java.sql.*;

/**
 * class to interact with a MariaDB database
 * @author Severin Rosner
 */
public class DataBase {

    /*
    Database table SQL:

        CREATE TABLE `UserData` (
        `user_id` INT NOT NULL AUTO_INCREMENT,
        `username` VARCHAR(15),
        `password` TEXT,
        `hs_classic` BIGINT DEFAULT NULL,
        `hs_time` BIGINT DEFAULT NULL,
        `hs_infinity` BIGINT DEFAULT NULL,
        `gamesPlayed` INT DEFAULT NULL,
        `timePlayed` INT DEFAULT NULL,
         PRIMARY KEY (`user_id`)
         );
     */

    /** load .env file */
    static Dotenv dotenv = Dotenv.configure().load();

    /** test db url **/
    private final static String TESTDB_URL = dotenv.get("TEST_DB_URL");
    /** test db user */
    private final static String TESTDB_USER = dotenv.get("TEST_DB_USER");
    /** test db pass */
    private final static String TESTDB_PASS = dotenv.get("TEST_DB_PW");

    /** javatetris db url */
    private final static String JAVADB_URL = dotenv.get("DATABASE_URL");
    /** javatetris db user */
    private final static String JAVADB_USER = dotenv.get("DATABASE_USER");
    /** javatetris db pass */
    private final static String JAVADB_PASS = dotenv.get("DATABASE_PW");

    /** url to connect to database */
    private final static String URL = TESTDB_URL;
    /** username to log into database */
    private final static String USER = TESTDB_USER;
    /** password to log into database */
    private final static String PASS = TESTDB_PASS;


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
            getConnection();
            System.out.println("Database.java: Connected successfully to" + URL);
            return true;
        } catch (SQLException e) {
            Main.errorAlert("DataBase.java");
            System.out.println("DataBase.java: " + e.getMessage());
            return false;
        }
    }

    /**
     * get a connection
     * @return connection to database
     * @throws SQLException sql exception
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + URL, USER, PASS);
        } catch (SQLException e) {
            Main.errorAlert("DataBase.java");
            e.printStackTrace();
            throw new SQLException("SQL Exception");
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

                Settings.setNewValue("accountType", "online", "settings");
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
                    Settings.setNewValue("accountType", "online", "settings");
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

    /**
     * load the data for a specific user from database
     * @param connection to database
     * @param username user to load data for
     */
    public static void loadOnlineUserData(Connection connection, String username) {

        try {
            String query = "SELECT * FROM UserData where username = ?";
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_Id");
                String dbusername = resultSet.getString("username");
                String password = resultSet.getString("password");
                BigDecimal hsClassic = resultSet.getBigDecimal("hs_Classic");
                BigDecimal hsTime = resultSet.getBigDecimal("hs_Time");
                BigDecimal hsInfinity = resultSet.getBigDecimal("hs_Infinity");
                int gamesPlayed = resultSet.getInt("gamesPlayed");
                BigDecimal timePlayed = resultSet.getBigDecimal("timePlayed");

                System.out.println("DataBase.java: Online UserData from '" + username + "': " + "ID: " + userId + " Username: " + dbusername + " Password: " + password + " HSClassic: " + hsClassic + " HSTime: " + hsTime + " HSInfinity: " + hsInfinity + " GamesPlayed: " + gamesPlayed + " TimePlayed: " + timePlayed);
            }

            statement.close();

        } catch (SQLException e) {
            Main.errorAlert("DataBase.java");
            e.printStackTrace();
        }
    }



}
