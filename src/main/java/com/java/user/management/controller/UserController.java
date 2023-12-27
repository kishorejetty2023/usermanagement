package com.java.user.management.controller;

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

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws ParseException {

        return userService.saveUser(userRequest);
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return userService.getAllUsers();

    }

    @GetMapping("/getUsersbyId/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") int id){

        log.info("Converted ID : {}", id);
        return userService.getUserById(id);
    }

    @PostMapping("/updateUserEmail")
    public ResponseEntity<String> updateEmailByUserIdOrId(@Valid @RequestBody UserUpdateRequest userUpdateRequest){

        return userService.updateUserEmailByLogIdOrId(userUpdateRequest);
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUserById(
            @RequestHeader(name = "x-adminId") String adminId, @PathVariable("id") int id){

        return userService.deleteUserById(id,adminId);
    }


}
