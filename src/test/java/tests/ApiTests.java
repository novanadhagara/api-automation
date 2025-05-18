package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    private static String token;
    private static String baseUrl = "https://whitesmokehouse.com";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        String email = "albertsimanjuntak12@gmail.com";
        String password = "@dmin123";

        Response res =
                given().contentType(ContentType.JSON)
                        .body("{" +
                                "\"email\": \"" + email + "\"," +
                                "\"password\": \"" + password + "\"}")
                        .when().post("/webhook/api/login")
                        .then().statusCode(200)
                        .extract().response();

        token = "Bearer " + res.jsonPath().getString("access_token");
    }

    @Test(priority = 1)
    public void testGetAllObjects() {
        given().header("Authorization", token)
                .when().get("/webhook/api/objects")
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(priority = 2)
    public void testAddObject() {
        given().header("Authorization", token)
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"name\":\"Apple MacBook Pro 16\"," +
                        "\"data\": {\"year\": 2019,\"price\": 1849.99,\"cpu_model\": \"Intel Core i9\"," +
                        "\"hard_disk_size\": \"1 TB\",\"capacity\": \"2 cpu\",\"screen_size\": \"14 Inch\",\"color\": \"red\"}}")
                .when().post("/webhook/api/objects")
                .then().statusCode(200)
                .body("name", equalTo("Apple MacBook Pro 16"));
    }

    @Test(priority = 3)
    public void testUpdateObject() {
        given().header("Authorization", token)
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"name\":\"Updated MacBook\"," +
                        "\"data\": {\"year\": 2020,\"price\": 2000.00,\"cpu_model\": \"M1 Max\"," +
                        "\"hard_disk_size\": \"2 TB\",\"capacity\": \"8 cpu\",\"screen_size\": \"16 Inch\",\"color\": \"silver\"}}")
                .when().put("/webhook-test/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/3")
                .then().statusCode(200)
                .body("name", equalTo("Updated MacBook"));
    }

    @Test(priority = 4)
    public void testPatchObject() {
        given().header("Authorization", token)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Apple MacBook Pro Patched\", \"year\": \"2030\"}")
                .when().patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/12")
                .then().statusCode(200)
                .body("name", containsString("Patched"));
    }

    @Test(priority = 5)
    public void testDeleteObject() {
        given().header("Authorization", token)
                .when().delete("/webhook-test/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/3")
                .then().statusCode(200)
                .body("message", containsString("deleted"));
    }

    @Test(priority = 6)
    public void testGetAllDepartments() {
        given().header("Authorization", token)
                .when().get("/webhook/api/department")
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }
}

