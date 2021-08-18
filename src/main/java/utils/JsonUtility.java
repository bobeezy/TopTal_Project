package main.java.utils;

import com.google.gson.*;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import main.java.Engine.DriverFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtility extends DriverFactory {

    public static DocumentContext doc = null;

    //region <Load json file from directory>
    public static Object loadJson(String filePath) {

        try {
            JSONParser parser = new JSONParser();

            //need to use a variable to store a file name
            return parser.parse(new FileReader(filePath));
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object loadJsonAsString(String apiString) {

        try {
            JSONParser parser = new JSONParser();

            //need to use a variable to store a file name
            Object object = parser.parse(apiString);

            return object;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region <updateJsonFile>
    /**
     * To update Json file with a String value
     */
    public static DocumentContext updateJsonFile(Object object, String jsonXpath, String value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithString>
    /**
     * To update Json file with a String value
     */
    public static DocumentContext updateJsonFileWithString(Object object, String jsonXpath, String value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithInt>
    /**
     * To update Json file with a int value
     */
    public static DocumentContext updateJsonFileWithInt(Object object, String jsonXpath, int value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithDouble>
    /**
     * To update Json file with a double value
     */
    public static DocumentContext updateJsonFileWithDouble(Object object, String jsonXpath, double value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithObject>
    /**
     * To update Json file with an Object value
     */
    public static DocumentContext updateJsonFileWithObject(Object object, String jsonXpath, Object value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithBoolean>
    /**
     * To update Json file with a boolean value
     */
    public static DocumentContext updateJsonFileWithBoolean(Object object, String jsonXpath, boolean value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <updateJsonFileWithLong>
    /**
     * To update Json file with a long value
     */
    public static DocumentContext updateJsonFileWithLong(Object object, String jsonXpath, long value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <Convert documentContext to json object>
    /**
     *Use this method when the Json payload is a normal Json payload
     */
    public static JsonObject convertDocumentContextToJsonObject(DocumentContext jsonObject) {

        try {
            return new GsonBuilder().create().toJsonTree(jsonObject.json()).getAsJsonObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *Use this method when the Json payload is a List/Array
     */
    public static JsonArray convertDocumentContextToJsonArray(DocumentContext jsonObject) {

        try {
            return new GsonBuilder().create().toJsonTree(jsonObject.json()).getAsJsonArray();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region <Convert string to json object>
    public static JSONObject convertStringToJsonObject(String jsonString) {

        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(jsonString);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region <convertStringToJson>
    /**
     * This method converts a String to Json payload
     * @param payload
     * @return
     */
    public String convertStringToJson(String payload) {

        JSONArray jsonArray = new JSONArray("[" + payload + "]");
        String convertedString = String.valueOf(jsonArray);
        return convertedString;
    }
    //endregion

    //region <convertStringToJsonAndExtractValue>
    /**
     * This method converts a String to Json, then
     * extract a value from the Json payload
     * @param payload
     * @param valueToExtract
     * @return
     */
    public String convertStringToJsonAndExtractValue(String payload, String valueToExtract) {

        JSONArray jsonArray = new JSONArray("[" + payload + "]");
        String extractedValue = jsonArray.getJSONObject(0).get(valueToExtract).toString();
        return extractedValue;
    }
    //endregion

    //region <Get from Json file>
    public static int getIntFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getInt(key);
    }
    //endregion

    //region <Read from Json file>
    /**
     * These methods are used to read unique keys from a json file
     */
    public static String readStringFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString(key);
    }

    public static Boolean readBoolean_FromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Boolean value = jsonObject.getBoolean(key);
        return value;
    }

    public static Boolean readBooleanFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getBoolean(key);
    }

    public static Double readDoubleFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getDouble(key);
    }

    public static Integer readIntegerFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getInt(key);
    }

    public static Long readLongFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getLong(key);
    }

    public static Object readObjectFromJson(String jsonString, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject(key);
    }
    //endregion

    //region <Read from Json file using Parse>
    /**
     * These methods are used to read unique key from a json file
     * e.g: String fnb = json.parseJsonToString(jsObj, "customerdata", "fnb"); from path "customerdata.fnb"
     */
    public static String parseJsonToString(String jsonString, String stringPathToExtract, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject(stringPathToExtract).getString(key);
    }

    public static Integer parseJsonToInteger(String jsonString, String stringPathToExtract, String key) {

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject(stringPathToExtract).getInt(key);
    }


    /**
     * These methods are used to read unique key from a json file
     * e.g: String fnb = json.parseJsonPathToString(jsObj, "$.customerdata.fnb"); from path "customerdata.fnb"
     */
    public static String parseJsonToStringUsingJsonPath(String jsonString, String jsonPath) {

        String key = JsonPath.read(jsonString, jsonPath);
        return key;
    }

    public static Integer parseJsonToIntegerUsingJsonPath(String jsonString, String jsonPath) {

        int key = JsonPath.read(jsonString, jsonPath);
        return key;
    }
    //endregion

    //region <Read from Json file using Jsonpath>
    /**
     * in case we have multiple keys with the same name,
     * we use this method to read the key using its jsonpath
     */
    public String readStringFromJsonUsingXpath(String response, String returnKeyXpath) {

        String return_parameter = "";

        returnKeyXpath = returnKeyXpath.replaceAll("\\$\\.", "");
        String[] anArray = returnKeyXpath.split("\\.");
        try {
            if (response == " " || response == null) {
                return null;
            }

            JsonParser json_Parsed = new JsonParser();
            for (int i = 0; i < anArray.length; i++) {

                JsonObject jsonObject = (JsonObject) json_Parsed.parse(response);
                if (jsonObject != null) {
                    String currentTag = anArray[i];
                    JsonObject jsonObj = jsonObject.getAsJsonObject();
                    JsonElement jsonObject1 = jsonObj.get(currentTag);
                    return_parameter = jsonObject1.toString();
                }

                response = return_parameter;
            }

            return_parameter = return_parameter.replace("\"", "");
            return return_parameter;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion
}
