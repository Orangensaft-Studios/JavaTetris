package at.javatetris.project;

public class UserDataOnline {

    public static String[] update(String username) {
        String data = DataBaseAPI.getData(username);
        System.out.println(data);
        return new String[] {getJSONValue(data, "hs_classic"), getJSONValue(data, "hs_time"), getJSONValue(data, "hs_infinity"), getJSONValue(data, "timePlayed"), getJSONValue(data, "hs_infinity")};
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
