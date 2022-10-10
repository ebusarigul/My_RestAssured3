package Campus;

import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CountryTest {

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

    String countryID;
    String countryName;
    String countryCode;

    @Test
    public void createCountry() {

        countryName = getRandomName();
        countryCode = getRandomCode();

        Country country = new Country();
        country.setName(countryName);  // generateCountryName;
        country.setCode(countryCode);  // generateCountryCode;

        countryID =

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)

                        .when()
                        .post("school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

        ;
    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }


    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {

        //  "message": "The Country with Name \"Israel 141\" already exists.",

        Country country = new Country();
        country.setName(countryName);
        country.setCode(countryCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("The Country with Name \""+countryName+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry() {

        countryName = getRandomName();
        countryCode = getRandomCode();

        Country country = new Country();
        country.setId(countryID);
        country.setName("ebu " + countryName);
        country.setCode("code "+ countryCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("ebu " + countryName))
        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountryById() {

        given()
                .cookies(cookies)
                .pathParam("countryID",countryID)

                .when()
                .delete("school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(208)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void deleteCountryByIdNegative() {

        given()
                .cookies(cookies)
                .pathParam("countryID",countryID)

                .when()
                .delete("school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void updateCountryNegative() {

        countryName = getRandomName();
        countryCode = getRandomCode();

        Country country = new Country();
        country.setId(countryID);
        country.setName("ebunega " + countryName);
        country.setCode("codenega "+ countryCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))

        ;
    }

}
