package POJO;

import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {
        given()

                // hazırlık işlemlerini burada yapacagız
                // (token, send body, parametreler)

                .when()
                // linki ve metodu veriyoruz

                .then();
                // assetion ve verileri ele alma exract kısmı

    }
    @Test
    public void statusCodeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()        // bütün respons u gösterir
                .statusCode(200); // status kontrolu
    }
    @Test
    public void contentTypeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
    @Test
    public void checkStateInResponseBody() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .statusCode(200)
                ;
    }
    @Test
    public void bodyJsonPathTest2() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
        ;
    }
    @Test
    public void bodyJsonPathTest3() {
        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'", hasItem("Camuzcu Köyü")) // has ıtem : places ların içersinde place name i camuzcu koyu olan var mı
                .statusCode(200)
        ;

    }
    @Test
    public void bodyArrayHasSızeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))  // verilen path deki listin size kontrolu
                .statusCode(200)
        ;
    }
    @Test
    public void combiningTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state",hasItem("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }
    @Test
    public void pathParamTest() {
        given()
                .pathParam("countryCode","us")
                .pathParam("zipCode","90210")
                .log().uri()  // http://api.zippopotam.us/us/90210

                .when()
                .get("http://api.zippopotam.us/{countryCode}/{zipCode}")

                .then()
                .log().body()

                .statusCode(200)
        ;
    }
    @Test
    public void pathParamTest2() {
        //90210 dan 90250 e kadar test sonuclarında places size ının hepsinde 1 geldigini test ediniz

        for (int i = 90210; i <=90213 ; i++) {
            given()
                    .pathParam("countryCode","us")
                    .pathParam("zipCode",i)
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{countryCode}/{zipCode}")

                    .then()
                    .log().body()
                    .body("places",hasSize(1))

                    .statusCode(200)
            ;
        }
    }
    @Test
    public void queryParamTest() {
        given()
                .param("page",1)
                .log().uri()  // https://gorest.co.in/public/v1/users?page=1

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200)
        ;
    }


    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    void Setup(){

        baseURI="https://gorest.co.in/public/v1";

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();

    }
    @Test
    public void requestResponseSpecs() {
        given()
                .param("page",1)
                .spec(requestSpecification)

                .when()
                .get("/users") // url in basında http yoksa baseURI deki değer otomatik gelir ve /users ı sonuna ekler

                .then()
                .body("meta.pagination.page",equalTo(1))
                .spec(responseSpecification)
        ;
    }

    @Test
    public void extractingJsonPath() {

        String placeName=

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .statusCode(200)
                .extract().path("places[0].'place name'")
                //extract metodu ile givenla baslayan satır, bir deger döndürür hale geldi. en sonda extract olmalı
        ;
        System.out.println("placeName = " + placeName);
    }

    @Test
    public void extractingJsonPathInt() {

        int limit=

                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");


        System.out.println("limit = " + limit);
        Assert.assertEquals(limit,10,"test result");
    }

    @Test
    public void extractingJsonPathIntList() {

        List<Integer> idler=

                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id"); // datadaki bütün id leri bir list seklinde verir

        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(3043),"test result");
    }

    @Test
    public void extractingJsonPathStringList() {

        List<String> names=

                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name"); // datadaki bütün name leri bir list seklinde verir

        System.out.println("names = " + names);
        Assert.assertTrue(names.contains("Navin Patil"),"test result");
    }

    @Test
    public void extractingResponseAll() {

        Response response=

                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response(); // bütün body alındı


        List<Integer> ids = response.path("data.id");
        List<String> names = response.path("data.name");
        int limit = response.path("meta.pagination.limit");

        System.out.println("ids = " + ids);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);


    }

    @Test
    public void extractingJsonPOJO() {

        Location yer =

                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().as(Location.class) //location sablonu
                ;

        System.out.println("yer = " + yer);
        System.out.println("yer.getCountry() = " + yer.getCountry());
        System.out.println("yer.getPlaces().get(0).getPlacename() = " + yer
                .getPlaces().get(0).getPlacename());

    }

}
