package POJO.task_3;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class task3 {

    /**
     * Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void task3(){
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .body("title", equalTo("quis ut nam facilis et officia qui"))
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }

    /** Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * expect content type JSON
     * expect third item have:
     *      title = "fugiat veniam minus"
     *      userId = 1
     */

    @Test
    public void task4(){
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                //.log().body()
                .body("[2].title", equalTo("fugiat veniam minus"))
                .body("[2].userId",equalTo(1))
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;

    }

}
