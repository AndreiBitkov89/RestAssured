package tests;

import controllers.UserController;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import utils.SharedSteps;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UserTests {

    private static UserController userController;
    private User user;
    private int userId = 0;
    private final SharedSteps sharedSteps = new SharedSteps();

    @BeforeAll
    public static void setupClass() {
        userController = new UserController();
    }

    @BeforeEach
    public void setupTest() {
        user = UserGenerator.generateRandomUser();
    }

    @AfterEach
    public void cleanupTest() {
        if (userId != 0) {
            userController.deleteUser(userId).then().statusCode(anyOf(is(204), is(404)));
            userId = 0;
        }
    }


    @Test
    @DisplayName("1. Create and get user")
    public void createUserTest() {
        userId = sharedSteps.createTestUser(userController, user);

        userController.getUser(userId).then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()));
    }

    @Test
    @DisplayName("2. Get 422 when creating user with duplicate email")
    public void createNotUniqueUserTest() {
        userId = sharedSteps.createTestUser(userController, user);

        Response createDuplicate = userController.createUser(user);
        createDuplicate.then()
                .statusCode(422)
                .body("[0].field", equalTo("email"))
                .body("[0].message", equalTo("has already been taken"));
    }

    @Test
    @DisplayName("3. Validate get user data")
    public void getUserTest() {
        userId = sharedSteps.createTestUser(userController, user);

        userController.getUser(userId).then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()));
    }

    @Test
    @DisplayName("4. Update user")
    public void updateUserTest() {
        userId = sharedSteps.createTestUser(userController, user);

        User updatedUser = UserGenerator.generateRandomUser();

        userController.updateUser(userId, updatedUser).then()
                .statusCode(200)
                .body("name", equalTo(updatedUser.getName()))
                .body("email", equalTo(updatedUser.getEmail()))
                .body("gender", equalTo(updatedUser.getGender()))
                .body("status", equalTo(updatedUser.getStatus()));

        userController.getUser(userId).then()
                .statusCode(200)
                .body("name", equalTo(updatedUser.getName()))
                .body("email", equalTo(updatedUser.getEmail()))
                .body("gender", equalTo(updatedUser.getGender()))
                .body("status", equalTo(updatedUser.getStatus()));
    }

    @Test
    @DisplayName("5. Delete user")
    public void deleteUserTest() {
        userId = sharedSteps.createTestUser(userController, user);

        userController.deleteUser(userId).then()
                .statusCode(204)
                .body(equalTo(""));

        userController.getUser(userId).then().statusCode(404);

        userId = 0;
    }
}
