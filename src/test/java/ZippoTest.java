import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ZippoTest {

    @Test
    public void zippoTest() {


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
                .statusCode(200); // status kontrolü


    }


}
