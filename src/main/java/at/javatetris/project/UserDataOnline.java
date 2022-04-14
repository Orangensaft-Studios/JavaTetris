package at.javatetris.project;

import java.util.ArrayList;
import java.util.List;

public class UserDataOnline {

    public static List<Player> updateData() {
        String data = DataBaseAPI.getAllData();
        System.out.println("UserDataOnline.java: updateData: " + data);

        List<Player> allData = new ArrayList<>();

        String[] dataUsers = data.split("}");

        for (String d : dataUsers) {
            int nameStart = d.indexOf(":");
            String name = d.substring(nameStart + 1, d.indexOf(",", nameStart)).replaceAll("\"", "").trim();

            int hsClassicStart = d.indexOf(":", nameStart + 1);
            int hsClassic = Integer.parseInt(d.substring(hsClassicStart + 1, d.indexOf(",", hsClassicStart)).trim());

            int hsTimeStart = d.indexOf(":", hsClassicStart + 1);
            int hsTime = Integer.parseInt(d.substring(hsTimeStart + 1, d.indexOf(",", hsTimeStart)).trim());

            int hsInfinityStart = d.indexOf(":", hsTimeStart + 1);
            int hsInfinity = Integer.parseInt(d.substring(hsInfinityStart + 1, d.indexOf(",", hsInfinityStart)).trim());

            int gamesPlayedStart = d.indexOf(":", hsInfinityStart + 1);
            int gamesPlayed = Integer.parseInt(d.substring(gamesPlayedStart + 1, d.indexOf(",", gamesPlayedStart)).trim());

            int timePlayedStart = d.indexOf(":", gamesPlayedStart + 1);
            int timePlayed = Integer.parseInt(d.substring(timePlayedStart + 1).trim());

            allData.add(new Player(name, hsClassic, hsTime, hsInfinity, timePlayed, gamesPlayed));
        }

        System.out.println(allData);

        return allData;
    }

    public static void saveDataUser(int value, String field) {
        System.out.println("UserDataOnline.java: Saving data: " + value + " to " + field);
        DataBaseAPI.saveDataToDB(Settings.searchSettings("username"), Settings.searchSettings("password"), field, value);
    }


    public static String[] getDataUser(String username) {
        String data = DataBaseAPI.getData(username);
        System.out.println(data);
        return new String[] {getJSONValue(data, "hs_classic"), getJSONValue(data, "hs_time"), getJSONValue(data, "hs_infinity"), getJSONValue(data, "timePlayed"), getJSONValue(data, "gamesPlayed")};
    }




    /**
     * get a value from a JSON string
     * @param jsonString the JSON string
     * @param dataBaseField the key/database field to search for
     * @return the value
     */

    public static String getJSONValue(String jsonString, String dataBaseField) {
        int beginIndex = jsonString.indexOf(dataBaseField);
        int endIndex = jsonString.indexOf("]", beginIndex);
        String returnValue = jsonString.substring(beginIndex, endIndex);
        System.out.println("UserDataOnline.java getJSONValue " + returnValue);
        returnValue = returnValue.replaceAll(dataBaseField + "\\\": \\[\\\"", "").replaceAll("\\\"", "").trim();
        if (returnValue.equals(dataBaseField + ":[")) {
            returnValue = "0";
        }
        System.out.println("DataBaseAPI: JSON Field Value: " + returnValue);
        return returnValue;
    }


}
