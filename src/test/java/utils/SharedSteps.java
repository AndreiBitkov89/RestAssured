package utils;

import controllers.UserController;
import io.restassured.response.Response;
import models.User;

public class SharedSteps {

    public int createTestUser(UserController userController, User user) {
        Response createResponse = userController.createUser(user);
        createResponse.then().statusCode(201);
        return createResponse.jsonPath().getInt("id");
    }
}
