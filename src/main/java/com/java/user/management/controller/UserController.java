package com.java.user.management.controller;

import com.java.user.management.aop.LogExecutionTime;
import com.java.user.management.constants.ErrorConstants;
import com.java.user.management.exceptions.CustomError;
import com.java.user.management.exceptions.CustomValidationException;
import com.java.user.management.exceptions.FieldErrorDetail;
import com.java.user.management.service.ExternalService;
import com.java.user.management.service.UserService;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final ExternalService externalService;

    private final UserService userService;

    @Operation(summary= "Create a new user with all required details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @PostMapping("/createUser")
    @LogExecutionTime(additionalMessage = "This is a createUser-Post Method")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws ParseException {

        return userService.saveUser(userRequest);
    }

    @Operation(summary= "Retrieve all users' details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @GetMapping("/getAllUsers")
    @LogExecutionTime(additionalMessage = "This is a getAllUsers-Get Method")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return userService.getAllUsers();

    }
    @Operation(summary= "Retrieve a specific user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid user ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @GetMapping("/getUsersbyId/{id}")
    @LogExecutionTime(additionalMessage = "This is a getUsersbyId-Get Method")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Optional<Integer> paramId){

        int id = paramId.orElseThrow(() -> new CustomValidationException(List.of(
                FieldErrorDetail.builder()
                        .field("id")
                        .code(ErrorConstants.MISSING_PARAMETER)
                        .message("ID is required").build()
                    )));
        log.info("Converted ID : {}", id);
        return userService.getUserById(id);
    }

    @Operation(summary= "Update a user's email by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Invalid update request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @PostMapping("/updateUserEmail")
    @LogExecutionTime(additionalMessage = "This is a updateUserEmail-Post Method")
    public ResponseEntity<String> updateEmailByUserIdOrId(@Valid @RequestBody UserUpdateRequest userUpdateRequest){

        return userService.updateUserEmailByLogIdOrId(userUpdateRequest);
    }

    @Operation(summary= "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or missing admin ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @DeleteMapping("/deleteUser/{id}")
    @LogExecutionTime(additionalMessage = "This is a deleteUser/{id}-Delete Method")
    public ResponseEntity<String> deleteUserById(
            @RequestHeader(name = "x-adminId") String adminId, @PathVariable("id") int id){

        return userService.deleteUserById(id,adminId);
    }

    @Operation(summary= "Call an external service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External service call successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomError.class)))
    })
    @GetMapping("/callAnotherBackend")
    @LogExecutionTime(additionalMessage = "This is a getMessage Method")
    public ResponseEntity<String> getMessage(){
        return externalService.callExternalService();
    }


}
