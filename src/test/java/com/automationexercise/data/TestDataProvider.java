package com.automationexercise.data;

import com.automationexercise.utils.ConfigReader;
import com.github.javafaker.Faker;

import java.util.Locale;

public class TestDataProvider {

    private static final Faker faker = new Faker(new Locale("en-US"));

    private TestDataProvider() {}

    // ===== DYNAMIC USER DATA =====
    public static String randomEmail() {
        return "test_" + System.currentTimeMillis() + "@gmail.com";
    }

    public static String randomName() {
        return faker.name().fullName();
    }

    public static String randomPassword() {
        return "test_" +  faker.number().digits(5);
    }

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomCompany() {
        return faker.company().name();
    }

    public static String randomAddress() {
        return faker.address().streetAddress();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomState() {
        return faker.address().state();
    }

    public static String randomZipCode() {
        return faker.address().zipCode().substring(0, 5);
    }

    public static String randomPhone() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    // ===== CARD DATA =====
    public static String cardName()   { return randomName(); }
    public static String cardNumber() { return "4111111111111111"; }
    public static String cardCVC()    { return "123"; }
    public static String cardMonth()  { return "12"; }
    public static String cardYear()   { return "2027"; }

    // EXISTING USER (from user.properties) =====
    public static String existingEmail() {
        return ConfigReader.get("existing.email");
    }

    public static String existingPassword() {
        return ConfigReader.get("existing.password");
    }

    public static String existingName() {
        return ConfigReader.get("existing.name");
    }
}