package main.java.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import main.java.Engine.DriverFactory;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static main.java.api.CustomHeaders.customHeadersMap;

public class APICommonMethods extends DriverFactory {

    public static String getValueFromJson(ValidatableResponse response, String jsonPath) {

        String value = response.extract().jsonPath().getString(jsonPath);
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        return value;
    }

    public static String getFirstValueFromJson(ValidatableResponse response, String jsonPath) {

        String value = response.extract().jsonPath().getString(jsonPath);
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        List<String> myList = new ArrayList<String>(Arrays.asList(value.split(",")));

        value = myList.get(0);
        return value;
    }

    public static int getFirstValueIntFromJson(ValidatableResponse response, String jsonPath) {

        String value = response.extract().jsonPath().getString(jsonPath);
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        List<String> myList = new ArrayList<>(Arrays.asList(value.split(",")));

        value = myList.get(0);
        int finalValue = Integer.parseInt(value);

        return finalValue;
    }

    public static String getValueFromJsonResp(String keyString) {

        String value = response.extract().path(keyString).toString();
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        return value;
    }

    public static String getValueFromJsonResp(ValidatableResponse response, String keyString) {
        String value = response.extract().path(keyString).toString();
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        return value;
    }

    public static List getListValueFromJsonResp(ValidatableResponse response, String keyString) {
        String value = response.extract().path(keyString).toString();
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        List valueList = Collections.singletonList(value);
        return valueList;
    }

    //region <GET method>
    public ValidatableResponse getMethod(String endpoint, Map header) {

        log("Method: GET\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                when().
                        get(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    /**
     *This method is to execute a Get Request using only the basic authentication
     */
    public ValidatableResponse getMethod(String endpoint, String username, String password) {

        log("Method: GET\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.port + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\nUsername: " + username + "\nPassword: " + password, "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        auth().
                        basic(username, password).
                when().
                        get(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    /**
     *This method is to execute a Get Request using cookies
     */
    public Response getMethod(String endpoint, Map header, String cookieValue, String branch_Code) {

        log("Method: GET\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");

        res =
                given().
                        spec(requestSpec).
                        cookies("branch-dashboard", cookieValue).
                        headers(customHeadersMap).
                when().
                        get(endpoint);

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log("Status Code: " + res.getStatusCode(), "INFO", "text");
        log(res.getBody().asString(), "INFO", "json");

        return res;
    }
    //endregion

    //region <POST method>
    public ValidatableResponse postImageMethod(File fileToUpload, String endpoint, Map header) {

        log("Method: POST\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(String.valueOf(fileToUpload), "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        multiPart(fileToUpload).
                when().
                        post(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse postMethod(String payload, String endpoint, Map header) {

        log("Method: POST\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        body(payload).
                when().
                        post(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse postMethod(String payload, String endpoint, Map header, String username, String password) {

        log("Method: POST\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- Basic Auth ---------------\nUsername : " + username + "\nPassword : " + password, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        auth().
                        basic(username, password).
                        headers(header).
                        body(payload).
               when().
                        post(endpoint).
               then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    /**
     *This method is to execute a Post Request with Form Param
     * @return
     */
    public ValidatableResponse postFormParamMethod(String endpoint, Map header, String name, String status) {

        log("Method: POST\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\nName: " + name + "\nStatus: " + status, "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("application/x-www-form-urlencoded", ContentType.TEXT))).
                        formParam("username", name).
                        formParam("password", status).
                when().
                        post(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }
    //endregion

    //region <PUT method>
    /**
     *This method is to execute a PUT Request without a payload
     */
    public ValidatableResponse putMethod(String endpoint, Map header) {

        log("Method: PUT\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        response =
                given().
                        spec(requestSpec)
                        .headers(header).
                when().
                        put(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    /**
     *This method is to execute a PUT Request with a payload
     */
    public ValidatableResponse putMethod(String payload, String endpoint, Map header) {

        log("Method: PUT\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        body(payload).
                when().
                        put(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse putMethod(String payload, String endpoint, Map header, String username, String password) {

        log("Method: PUT\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- Basic Auth ---------------\nUsername : " + username + "\nPassword : " + password, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        auth().
                        basic(username, password).
                        headers(header).
                        body(payload).
                        when().
                        put(endpoint).
                        then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public Response putResMethod(String payload, String endpoint, Map header, String username, String password) {

        log("Method: DELETE\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        res =
                given().
                        spec(requestSpec).
                        headers(customHeadersMap).
                        auth().
                        basic(username, password).
                        body(payload).
                        when().
                        put(endpoint);

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log("Status Code: " + res.getStatusCode(), "INFO", "text");
        log(res.getBody().asString(), "INFO", "json");

        return res;
    }

    /**
     *This method is to execute a PUT Request using custom Headers and
     *the basic authentication
     */
    public ValidatableResponse putMethod(String endpoint, String username, String password, Map header) {

        log("Method: PUT\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.port + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        auth().
                        basic(username, password).
                        when().
                        put(endpoint).
                        then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse putFormParamMethod(Map params, String endpoint, Map header) {

        log("Method: PUT\n----------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.port + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log("formParam: " + params, "INFO", "json");

        response =
                given().spec(requestSpec)
                        .headers(header)
                        .formParams(params).
                        when()
                        .put(endpoint).
                        then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse putFormParamMethod(String payload, String endpoint, Map header, String username, String password) {

        log("Method: PUT\n----------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.port + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        auth().
                        basic(username, password).
                        headers(header).
                        formParam("json", payload).
                        when().
                        put(endpoint).
                        then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }
    //endregion

    //region <DELETE method>
    public ValidatableResponse deleteMethod(String endpoint, Map header) {

        log("Method: DELETE\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                when().
                        delete(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public ValidatableResponse deleteWithCookieMethod(String endpoint, Map header, String cookieValue) {

        log("Method: DELETE\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        cookie(cookieValue).
                when().
                        delete(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }

    public Response deletePathParamMethod(String endpoint, Map header, String cookieValue, String branch_Code, String order_Id, String routeLeg_Id) {

        log("Method: DELETE\n---------------- URL ------------------\n"
                + RestAssured.baseURI + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");

        res =
                given().
                        spec(requestSpec).
                        cookies("branch-dashboard", cookieValue).
                        headers(customHeadersMap).
                        pathParams("code", branch_Code).
                        pathParams("orderId", Long.parseLong(order_Id)).
                        pathParams("routeLegId", Long.parseLong(routeLeg_Id)).
                        when().
                        delete(endpoint);

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log("Status Code: " + res.getStatusCode(), "INFO", "text");
        log(res.getBody().asString(), "INFO", "json");

        return res;
    }
    //endregion

    //region <PATCH method>
    public ValidatableResponse patchMethod(String payload, String endpoint, Map header) {

        log("Method: PATCH\n---------------- URL ------------------\n"
                + RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        body(payload).
                when().
                        patch(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }
    //endregion
}
