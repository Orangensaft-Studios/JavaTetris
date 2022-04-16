package at.javatetris.project;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * class to call to api's to get/write data from/to database
 * @author Severin Rosner
 */
public class DataBaseAPI {
    /** response code 500 */
    private static final int RESPONSE_CODE_500 = 500;
    /** response code 404 */
    private static final int RESPONSE_CODE_404 = 404;
    /** response code 200 */
    private static final int RESPONSE_CODE_200 = 200;
    //TODO bei allen rückgabe schauen wenn NoConnection, dh. mal alles ohne Internet probieren
    /**
     * create api call to create user
     * @param username to create
     * @param password for user
     * @return usrAlrExists | Errror | noUsrOrPassw | AwC | error1
     */
    public static String createUser(String username, String password) {
        System.out.println("DataBaseAPI.java: Trying to create user '" + username + "'");
        try {
            URL url = new URL("https://80122.wayscript.io/createUser?username=" + username + "&password=" + password);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI.java: Response code is: " + responseCode);

            if (responseCode == RESPONSE_CODE_500) {
                //no password or user given, shouldn't occur anyway
                conn.disconnect();
                return "noUsrOrPassw";
            } else if (responseCode == RESPONSE_CODE_404) {
                //user already exists
                return "UsrAlrExists";
            } else if (responseCode != RESPONSE_CODE_200) {
                conn.disconnect();
                return "Error";
            } else {
                //successful
                Settings.setNewValue("accountType", "online", "settings");
                conn.disconnect();
                System.out.println("DataBaseAPI.java: Created account '" + username + "'");
                return "AwC";
            }

        } catch (Exception e) {
            Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "Error";
        }
    }

    /**
     * create api call to login user (check passwords)
     * @param username to login
     * @param password password to check
     * @return if user logged in, user or password false, not logged in
     */
    public static String onlineLogin(String username, String password) {
        try {
            URL url = new URL("https://80269.wayscript.io/login?username=" + username + "&password=" + password);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI.java: Response code is: " + responseCode);

            if (responseCode == RESPONSE_CODE_500) {
                conn.disconnect();
                return "noUsrOrPassw";
            } else if (responseCode == RESPONSE_CODE_404) {
                conn.disconnect();
                return "userOrPasswordFalse";
            } else if (responseCode != RESPONSE_CODE_200) {
                conn.disconnect();
                Main.errorAlert("DataBaseAPI.java");
                return "Error";
            } else {
                conn.disconnect();

                //return successful login
                System.out.println("DataBaseAPI.java: Logged in with '" + username + "'");
                Settings.setNewValue("accountType", "online", "settings");
                return "loggedIn";
            }
        } catch (UnknownHostException e) {
            //e.g. when no internet
            return "NoConnection";
        } catch (Exception e) {
            Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * create api call to save data
     * @param username to save data for
     * @param password to check if valid
     * @param field to store value to
     * @param value the new value
     * @return if successful
     */
    public static String saveDataToDB(String username, String password, String field, int value) {
        try {
            String urlString = String.format("https://80458.wayscript.io/saveData?username=%s&password=%s&field=%s&value=%s", username, password, field, value);
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI.java: Response code is: " + responseCode);

            if (responseCode == RESPONSE_CODE_500) {
                //no password or user given, shouldn't occur anyway
                conn.disconnect();
                return "noUsrOrPassw";
            } else if (responseCode == RESPONSE_CODE_404) {
                //user already exists
                return "UsrAlrExists";
            } else if (responseCode != RESPONSE_CODE_200) {
                conn.disconnect();
                return "Error";
            } else {
                conn.disconnect();
                //successful
                return "success";
            }
        } catch (UnknownHostException e) {
            return "NoConnection";
        } catch (IOException e) {
            //Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "error1";
        } catch (Exception e) {
            //Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "Error";
        }
    }

    /**
     * get all scores from database
     * @return string in json format with all data
     * @throws RuntimeException error
     */
    public static String getAllData() {
        //data will store the JSON data streamed in string format
        String response = "";

        try {
            URL url = new URL("https://81087.wayscript.io/getAllData");
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI.java: Response code is: " + responseCode);


            if (responseCode != RESPONSE_CODE_200) {
                conn.disconnect();
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    response += sc.nextLine();
                }
                sc.close();
            }

            conn.disconnect();

            return response;

        } catch (UnknownHostException e) {
            //e.g. when no internet
            return "NoConnection";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
    }

    /**
     * create api call to get user data
     * @param username to get data from
     * @return the data as string (in json format!)
     */
    public static String getData(String username) {
        String data = "";

        try {
            URL url = new URL("https://80114.wayscript.io/getUserData?username=" + username);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI.java: Response code is: " + responseCode);

           if (responseCode != RESPONSE_CODE_200) {
                conn.disconnect();
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    data += sc.nextLine();
                }

                System.out.println("DataBaseAPI.java: data: " + data);

                sc.close();
            }

            conn.disconnect();

            return data;

        } catch (UnknownHostException e) {
            //e.g. when no internet
            return "NoConnection";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
