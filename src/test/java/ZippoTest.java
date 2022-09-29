import io.restassured.http.ContentType;
import org.testng.annotations.Test;

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


}
