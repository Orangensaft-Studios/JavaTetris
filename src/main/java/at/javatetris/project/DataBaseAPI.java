package at.javatetris.project;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DataBaseAPI {

    public static boolean checkConnection() {
        try {
            URL url = new URL("https://80275.wayscript.io/checkConnection");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Main.errorAlert("DataBaseAPI.java");
            return false;
        }
    }

    public static String createUser(String username, String password) {
        System.out.println("DataBaseAPI.java: Trying to create user '" + username + "'");
        try {
            URL url = new URL("https://80122.wayscript.io/createUser?username=" + username + "&password=" + password);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI: Response code is: " + responseCode);

            if (responseCode == 500) {
                //no password or user given, shouldn't occur anyway
                conn.disconnect();
                return "noUsrOrPassw";
            } else if (responseCode == 404) {
                //user already exists
                return "UsrAlrExists";
            } else if (responseCode != 200) {
                conn.disconnect();
                return "Error";
            } else {
                //successful
                Settings.setNewValue("accountType", "online", "settings");
                conn.disconnect();
                System.out.println("DataBaseAPI.java: Created account '" + username + "'");
                return "AwC";
            }
        } catch (IOException e) {
            return "error1";
        } catch(Exception e) {
            Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "Error";
        }
    }

    public static String onlineLogin(String username, String password) {
        try {
            URL url = new URL("https://80269.wayscript.io/login?username=" + username + "&password=" + password);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI: Response code is: " + responseCode);

            if (responseCode == 500) {
                conn.disconnect();
                return "noUsrOrPassw";
            } else if (responseCode == 404) {
                return "userOrPasswordFalse";
            } else if (responseCode != 200) {
                conn.disconnect();
                Main.errorAlert("DataBaseAPI.java");
                return "Error";
            } else {
                conn.disconnect();

                getData(username);
                //return successful login
                System.out.println("DataBaseAPI.java: Logged in with '" + username + "'");
                Settings.setNewValue("accountType", "online", "settings");
                return "loggedIn";
            }
        } catch (UnknownHostException e) {
            //e.g. when no internet
            return "NoConnection";
        } catch(Exception e) {
            Main.errorAlert("DataBaseAPI.java");
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static String getData(String username) {
        //data will store the JSON data streamed in string format
        String data = "";

        try {
            URL url = new URL("https://80114.wayscript.io/getUserData?username=" + username);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("DataBaseAPI: Response code is: " +responseCode);

            if (responseCode == 404) {
                throw new RuntimeException("No username | HttpResponseCode: " + responseCode);
            } else if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner sc = new Scanner(url.openStream());

                while(sc.hasNext()) {
                    data += sc.nextLine();
                }

                System.out.println("DataBaseAPI: data: " + data);

                sc.close();
            }

            conn.disconnect();

            return data;

        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getJSONValue(String JSONString, String DataBaseField) {
        int beginIndex = JSONString.indexOf(DataBaseField);
        int endIndex = JSONString.indexOf(",", beginIndex);
        String returnValue = JSONString.substring(beginIndex, endIndex);
        returnValue = returnValue.replaceAll(DataBaseField + "\\\": \\[\\\"", "").replaceAll( "\\\"\\]", "").trim();
        System.out.println("DataBaseAPI: JSON Field Value: " + returnValue);
        return returnValue;
    }
}
