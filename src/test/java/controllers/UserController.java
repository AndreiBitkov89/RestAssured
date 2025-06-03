package controllers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class UserController {

    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String TOKEN = "Bearer d86df00b860fd143f8bdb15828c4a6580c31a8f4422316c18d6e433c944f8420";

    private static RequestSpecification baseRequest() {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", TOKEN);
    }

    @Step("Create user: {user}")
    public Response createUser(User user) {
        return baseRequest()
                .body(user)
                .when()
                .post("/users");
    }

    @Step("Get user by id: {id}")
    public Response getUser(int id) {
        return baseRequest()
                .when()
                .get("/users/{id}", id);
    }

    @Step("Delete user by id: {id}")
    public Response deleteUser(int id) {
        return baseRequest()
                .when()
                .delete("/users/{id}", id);
    }

    @Step("Update user by id: {id}")
    public Response updateUser(int id, User user) {
        return baseRequest()
                .body(user)
                .when()
                .put("/users/{id}", id);
    }
}
