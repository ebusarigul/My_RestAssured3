package POJO.task_1;

import POJO.task_1.task1;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Tasks {

    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */

    @Test
    public void writeBody(){
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body();
    }

    @Test
    public void test1() {

        task1 task1 =

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .extract().as(task1.class) //location sablonu
                ;

        System.out.println("task1 = " + task1);
        System.out.println("task1.getUserId() = " + task1.getUserId());
        System.out.println("task1.getTitle() = " + task1.getTitle());
        System.out.println("task1.getId() = " + task1.getId());
        System.out.println("task1.isCompleted() = " + task1.isCompleted());

    }


}
