package com.appsdeveloperblog.service;

import com.appsdeveloperblog.io.UsersDatabase;
import com.appsdeveloperblog.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";


    @BeforeAll
    void setup() {
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();

        userService = new UserServiceImpl(usersDatabase);

    }

    @AfterAll
    void cleanup() {
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {

        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Doe");

        //Act
        createdUserId = userService.createUser(user);

        //Assert
        assertNotNull(createdUserId, "User Id should not be null");


    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "John");
        newUserDetails.put("lastName", "Doeb");

        //Act
        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        //Assert
        assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName"), "Returned first name should be the same as the updated first name");
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        Map userDetails = userService.getUserDetails(createdUserId);

        //Assert
        assertNotNull(userDetails, "User details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"), "User Id should be the same as the created user id");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
         userService.deleteUser(createdUserId);

        //Assert
     assertNull(userService.getUserDetails(createdUserId), "User details should be null after deletion");
    }

}
