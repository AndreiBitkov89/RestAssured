package tests;

import controllers.UserController;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

public class UserTests {

    private static UserController userController;

    @BeforeAll
    public static void setup() {
        userController = new UserController();
    }

    @Test
    public void createUserTest() {
        User user = UserGenerator.generateRandomUser();

        Response createResponse = userController.createUser(user);
        createResponse.then()
                .statusCode(201)
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()))
                .body("id", notNullValue());

        int userId = createResponse.jsonPath().getInt("id");

        Response getResponse = userController.getUser(userId);
        getResponse.then()
                .statusCode(200)
                .body("id", equalTo(userId));
    }

    @Test
    public void createNotUniqueUserTest() {
        User user = UserGenerator.generateRandomUser();

        userController.createUser(user).then().statusCode(201);

        Response createResponse = userController.createUser(user);
        createResponse.then()
                .statusCode(422)
                .body("[0].field", equalTo("email"))
                .body("[0].message", equalTo("has already been taken"));
    }

    @Test
    public void getUserTest() {
        User user = UserGenerator.generateRandomUser();

        Response createResponse = userController.createUser(user);
        int userId = createResponse.jsonPath().getInt("id");

        Response getResponse = userController.getUser(userId);
        getResponse.then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()));
    }

    @Test
    public void deleteUserTest() {
        User user = UserGenerator.generateRandomUser();

        Response createResponse = userController.createUser(user);
        int userId = createResponse.jsonPath().getInt("id");

        Response deleteResponse = userController.deleteUser(userId);
        deleteResponse.then()
                .statusCode(204)
                .body(equalTo(""));

        Response getResponse = userController.getUser(userId);
        getResponse.then()
                .statusCode(404);
    }
}
