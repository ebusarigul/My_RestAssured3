package Campus;

import Campus.Model.Locations;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Campus.Methods.*;
import static io.restassured.RestAssured.*;

public class LocationsTest {

    Cookies cookies;

    @BeforeClass
    public void loginCampus() {

        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)


                        .when()
                        .post("auth/login")


                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
    }

    String locationsID;
    String locationsName;
    String locationsShortName;
    int capacity;
    String type;

    @Test(dependsOnMethods = "selectSchool")
    public void createLocations() {

        locationsName = getRandomName();
        locationsShortName = getRandomShortName();
        capacity = getRandomInt();
        type = "CLASS";

        Locations locations = new Locations();
        locations.setName(locationsName);
        locations.setShortName(locationsShortName);
        locations.setType(type);
        locations.setCapacity(capacity);


        locationsID =

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(locations)

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }


}
