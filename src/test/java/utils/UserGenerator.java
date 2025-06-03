package utils;

import models.User;

import java.util.Random;

public class UserGenerator {
    private static final Random random = new Random();

    public static User generateRandomUser() {
        String name = "User_" + random.nextInt(100000);
        String email = name.toLowerCase() + "@example.com";
        String gender = random.nextBoolean() ? "male" : "female";
        String status = "active";

        return new User(name, email, gender, status);
    }
}
