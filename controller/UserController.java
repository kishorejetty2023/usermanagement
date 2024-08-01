package com.java.user.management.controller;

import com.java.user.management.aop.LogExecutionTime;
import com.java.user.management.service.ExternalService;
import com.java.user.management.service.UserService;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private ExternalService externalService;
    private UserService userService;

    public UserController(UserService userService, ExternalService externalService){
        this.userService = userService;
        this.externalService = externalService;
    }

    @PostMapping("/createUser")
    @LogExecutionTime(additionalMessage = "This is a createUser-Post Method")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws ParseException {

        return userService.saveUser(userRequest);
    }


    @GetMapping("/getAllUsers")
    @LogExecutionTime(additionalMessage = "This is a getAllUsers-Get Method")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return userService.getAllUsers();

    }

    @GetMapping("/getUsersbyId/{id}")
    @LogExecutionTime(additionalMessage = "This is a getUsersbyId-Get Method")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") int id){

        log.info("Converted ID : {}", id);
        return userService.getUserById(id);
    }

    @PostMapping("/updateUserEmail")
    @LogExecutionTime(additionalMessage = "This is a updateUserEmail-Post Method")
    public ResponseEntity<String> updateEmailByUserIdOrId(@Valid @RequestBody UserUpdateRequest userUpdateRequest){

        return userService.updateUserEmailByLogIdOrId(userUpdateRequest);
    }


    @DeleteMapping("/deleteUser/{id}")
    @LogExecutionTime(additionalMessage = "This is a deleteUser/{id}-Delete Method")
    public ResponseEntity<String> deleteUserById(
            @RequestHeader(name = "x-adminId") String adminId, @PathVariable("id") int id){

        return userService.deleteUserById(id,adminId);
    }

    @GetMapping("callAnotherBackend")
    @LogExecutionTime(additionalMessage = "This is a getMessage Method")
    public ResponseEntity<String> getMessage(){
        return externalService.callExternalService();
    }


}
