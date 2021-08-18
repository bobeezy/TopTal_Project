package main.java.config;

import main.java.Engine.DriverFactory;

public class GlobalEnums extends DriverFactory {

    public static String httpsString = "https://";
    public static String dogsBaseUrl = httpsString + property.returnPropVal_api(api_fileName, "dogsBaseUrlPath");
    public static String petStoreBaseUrl = httpsString + property.returnPropVal_api(api_fileName, "petBaseUrlPath");
    public static String topTalBaseUrl = httpsString + property.returnPropVal_api(api_fileName, "topTalBaseUrlPath");
    public static String dogsPath = property.returnPropVal_api(api_fileName, "dogsUrlPath");
    public static String petPath = property.returnPropVal_api(api_fileName, "petUrlPath");

    //region <API>
    public enum Environment {

        DOGS(dogsBaseUrl, dogsPath, "dogs"),
        PET(petStoreBaseUrl, petPath, "pet"),
        TOPTAL(petStoreBaseUrl, petPath, "toptal");

        public final String baseUrl;
        public final String path;
        public final String environmentName;

        //Setters
        Environment(String baseUrl, String path, String environmentName) {

            this.baseUrl = baseUrl;
            this.path = path;
            this.environmentName = environmentName;
        }
    }
    //endregion
}
