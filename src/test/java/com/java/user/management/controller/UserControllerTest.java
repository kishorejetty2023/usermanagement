package com.java.user.management.controller;


import com.java.user.management.service.UserService;
import com.java.user.management.util.JsonObjectUtils;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Mock
    private UserService userService;

//    @BeforeAll
//    public static void setup(){
//    }

    @Test
    public void testCreateUser() throws ParseException {
        UserRequest userRequest = JsonObjectUtils
                .convertToObject("data/UserRequest.json",UserRequest.class);
        UserResponse userResponse = JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class) ;
        when(userService.saveUser(userRequest)).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));
        ResponseEntity<UserResponse> response = userController.createUser(userRequest);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(userRequest.getUserId(), response.getBody().getUserId());
        verify(userService, atLeastOnce()).saveUser(userRequest);
    }
    @Test
    public void testGetAllUsers(){

        when(userService.getAllUsers()).thenReturn(getAllUserResponse());
        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();
        assertNotNull(response.getStatusCode());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("kishore87jetty@gmail.com",
                Objects.requireNonNull(response.getBody()).get(0).getEmailId());
        verify(userService, atLeastOnce()).getAllUsers();


    }


    @Test
    public void testGetUserById() {
        int userId = 1;
        UserResponse userResponse = JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class);

        when(userService.getUserById(userId)).thenReturn(
                ResponseEntity.ok(userResponse));

        ResponseEntity<UserResponse> response = userController.getUserById(Optional.of(userId));

        assertEquals(ResponseEntity.ok(userResponse), response);
        verify(userService, times(1)).getUserById(userId);
    }

    ResponseEntity<List<UserResponse>> getAllUserResponse() {
        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class));
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }

    @Test
    void testUpdateEmailByUserIdOrId_ValidRequest() {
        // Arrange
        UserUpdateRequest userUpdateRequest =UserUpdateRequest.builder().emailId("newemail@example.com").id(1).build();

        when(userService.updateUserEmailByLogIdOrId(userUpdateRequest))
                .thenReturn(new ResponseEntity<>("Updated email, records updated: 1", HttpStatus.ACCEPTED));
        // Act
        ResponseEntity<String> response = userController.updateEmailByUserIdOrId(userUpdateRequest);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Updated email, records updated: 1", response.getBody());
    }

    @Test
    void testDeleteUserById_Success() {
        // Arrange
        String adminId = "admin123";
        int id = 1;

        // Mock the response from userService.deleteUserById
        when(userService.deleteUserById(id, adminId))
                .thenReturn(new ResponseEntity<>("Deleted user successfully with Id : "+id, HttpStatus.OK));

        // Act
        ResponseEntity<String> response = userController.deleteUserById(adminId, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted user successfully with Id : 1", response.getBody());
    }


}
