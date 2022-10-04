package POJO.task_1;

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

        ToDo ToDo =

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .extract().as(ToDo.class) //location sablonu
                ;

        System.out.println("ToDo = " + ToDo);
        System.out.println("ToDo.getUserId() = " + ToDo.getUserId());
        System.out.println("ToDo.getTitle() = " + ToDo.getTitle());
        System.out.println("ToDo.getId() = " + ToDo.getId());
        System.out.println("ToDo.isCompleted() = " + ToDo.isCompleted());

    }


}
