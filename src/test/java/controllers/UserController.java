package controllers;

import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserController {
    private final String baseUrl = "https://gorest.co.in/public/v2";

    public Response createUser(User user) {
        return given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer d86df00b860fd143f8bdb15828c4a6580c31a8f4422316c18d6e433c944f8420")
                .body(user)
                .when()
                .post("/users");
    }

    public Response deleteUser(int id) {
        return given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer d86df00b860fd143f8bdb15828c4a6580c31a8f4422316c18d6e433c944f8420")
                .when()
                .delete("/users/" + id);
    }

    public Response getUser(int id) {
        return given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer d86df00b860fd143f8bdb15828c4a6580c31a8f4422316c18d6e433c944f8420")
                .when()
                .get("/users/" + id);
    }
}
