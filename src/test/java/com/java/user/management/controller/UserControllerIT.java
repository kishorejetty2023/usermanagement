package com.java.user.management.controller;

import com.java.user.management.service.ExternalService;
import com.java.user.management.service.UserService;
import com.java.user.management.util.JsonObjectUtils;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ExternalService externalService;


    @Test
    void createUserWithValidRequest() throws Exception {
        // Arrange
        UserRequest userRequest = JsonObjectUtils
                .convertToObject("data/UserRequest.json",UserRequest.class);
        UserResponse userResponse = JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class) ;
        when(userService.saveUser(userRequest))
                .thenReturn(new ResponseEntity<>(userResponse, HttpStatus.CREATED));

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonObjectUtils.toJson(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Kishore"))
                .andExpect(jsonPath("$.emailId").value("kishore87jetty@gmail.com"))
                .andExpect(jsonPath("$.userId").value("K080303"));
    }

    @Test
    void createUserWithInvalidRequest() throws Exception {
        // Arrange
        UserRequest userRequest = JsonObjectUtils
                .convertToObject("data/UserRequest.json",UserRequest.class);
        userRequest.setEmailId("1234gmail.com");

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonObjectUtils.toJson(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("emailId"))
                .andExpect(jsonPath("$.errors[0].code").value("INVALID_DATA"))
                .andExpect(jsonPath("$.errors[0].message").value("Email id is not valid"));
    }

    @Test
    void getAllUsersShouldGetListOfUsers() throws Exception {
        // Arrange
        List<UserResponse> userResponses = JsonObjectUtils
                .mapJsonToUserResponseList("data/getAllUsersResponse.json",UserResponse.class);

        when(userService.getAllUsers())
                .thenReturn(new ResponseEntity<>(userResponses, HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1)) // Assert the ID of the first user is 1
                .andExpect(jsonPath("$[0].firstName").value("Kishore")) // Assert firstName is "Kishore"
                .andExpect(jsonPath("$[0].lastName").value("Jetty")) // Assert lastName is "Jetty"
                .andExpect(jsonPath("$[0].emailId").value("kishore87jetty@gmail.com")) // Assert emailId
                .andExpect(jsonPath("$[0].dob").value("11-10-1987")) // Assert DOB
                .andExpect(jsonPath("$[0].userId").value("k080303")); // Assert userId
    }

    @Test
    void getUserByIdShouldUser() throws Exception {
        // Arrange
        UserResponse userResponse = JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class) ;
        int id = 1;

        when(userService.getUserById(id))
                .thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/getUsersbyId/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Kishore"))
                .andExpect(jsonPath("$.emailId").value("kishore87jetty@gmail.com"))
                .andExpect(jsonPath("$.userId").value("K080303"));
    }


    @Test
    void updateEmailByIdWithValidRequest() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setId(1);
        request.setEmailId("newemail@example.com");

        when(userService.updateUserEmailByLogIdOrId(request))
                .thenReturn(ResponseEntity.ok("Updated email, records updated: 1"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/updateUserEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonObjectUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Updated email, records updated: 1"));
    }
    @Test
    void updateEmailByIdWithInvalidEmail() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setId(1);
        request.setEmailId("newemailexample.com"); // Invalid email format


        // Act & Assert
        mockMvc.perform(post("/api/v1/users/updateUserEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonObjectUtils.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("emailId"))
                .andExpect(jsonPath("$.errors[0].code").value("INVALID_DATA"))
                .andExpect(jsonPath("$.errors[0].message").value("Email id is not valid"));
    }

}
