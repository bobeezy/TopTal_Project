package main.java.api;

import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;
import main.java.Engine.DriverFactory;

import static main.java.api.CustomHeaders.buildCustomHeaders;
import static main.java.api.CustomHeaders.customHeadersMap;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.hasItems;

public class TaskOneMethods extends DriverFactory {

    APICommonMethods api = new APICommonMethods();

    public ValidatableResponse getListOfAllBreeds() {

        log("========================== Get List Of All Breeds ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_all_breeds_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getListOfSubBreeds() {

        log("========================== Get List Of Sub-Breeds ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_all_subBreeds_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getImageRandom() {

        log("========================== Get Random Image ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_random_image_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    /**
     * validations
     */
    //region <validateGetLocationSuccessfully>
    public void validateGetListOfAllBreeds(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");
    }
    //endregion

    //region <validateVerifyRetriever>
    public void validateVerifyRetriever(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String retriever = APICommonMethods.getValueFromJson(response, "message.retriever");

        response.assertThat().body("message.retriever", hasItems("chesapeake", "curly", "flatcoated", "golden"));
        log("ASSERT: Verify retriever is within list \nEXPECTED: chesapeake, curly, flatcoated, golden \nACTUAL: " + retriever, "INFO", "text");
    }
    //endregion

    //region <validateGetListOfSubBreeds>
    public void validateGetListOfSubBreeds(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String message = APICommonMethods.getValueFromJson(response, "message");

        Assert.assertNotNull(message);
        log("ASSERT: list of sub-breeds for “retriever” is present \nEXPECTED: Must not be null \nACTUAL: " + message, "INFO", "text");
    }
    //endregion

    //region <validateGetImageRandom>
    public void validateGetImageRandom(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String imageLink = APICommonMethods.getValueFromJson(response, "message");
//        String imageLink = APICommonMethods.getValueFromJsonResp(response, "message");
//        int branchOwnerTypeId = response.extract().jsonPath().getInt(branch_ownerTypeId);

        response.assertThat().body("message", containsStringIgnoringCase(".jpg"));
        log("ASSERT: link of Random Image is present \nEXPECTED: Must have a .jpg file \nACTUAL: " + imageLink, "INFO", "text");
    }
    //endregion
}
