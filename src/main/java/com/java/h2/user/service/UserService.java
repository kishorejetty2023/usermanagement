package com.java.h2.user.service;

import com.java.h2.user.entity.UserEntity;
import com.java.h2.user.exceptions.CustomValidationException;
import com.java.h2.user.mappers.UserMapper;
import com.java.h2.user.util.CustomUtils;
import com.java.h2.user.vo.UserRequest;
import com.java.h2.user.vo.UserResponse;
import com.java.h2.user.vo.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.java.h2.user.repository.UserRepository;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Value("${api.admin.id}")
    private String adminID;
    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserService(UserRepository userRepository,
                                UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserResponse> saveUser(UserRequest userRequest) throws ParseException {

        log.info("calling Mappers");
        log.info("Actual date : {}",userRequest.getDob());
        log.info("Converting to SQL Date {}",CustomUtils.convetStringDateToSqlDate(userRequest.getDob()));
        UserEntity userEntity = userRepository.save(userMapper.userRequestToEntity(userRequest));
        return new ResponseEntity<>(userMapper.userEnityToResposne(userEntity), HttpStatus.CREATED);
    }



    public ResponseEntity<List<UserResponse>> getAllUsers(){
        
        List<UserResponse>  users = userMapper.map(userRepository.findAll());
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(int id){
            userMapper.userEnityToResposne(userRepository.findById(id).get());
        return new ResponseEntity<>(userMapper.userEnityToResposne(userRepository.findById(id).get()),HttpStatus.OK);
    }

    public ResponseEntity<String> updateUserEmailByLogIdOrId(UserUpdateRequest userUpdateRequest){

        validateInputs(userUpdateRequest);
            int i = userRepository.updateEmail(userUpdateRequest.getEmailId(),userUpdateRequest.getId());
            String response = i>0?"Updated email, records updated: "+i:"No records found with Id: "+userUpdateRequest.getId();
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }



    public ResponseEntity<String> deleteUserById(int id, String xAdmin){

        System.out.println("Admin ID from yml : "+xAdmin);
        if(!CustomUtils.isValidAdminId(xAdmin,adminID)){
            throw new CustomValidationException("Non Admins cannot delete user");
        }
            userRepository.deleteById(id);

        return new ResponseEntity<>("Deleted user successfully with Id : "+id,HttpStatus.OK);
    }

    private void validateInputs(UserUpdateRequest userUpdateRequest) {

        if(userUpdateRequest==null
                || userUpdateRequest.getId()<=0){
            log.error("either UserId or Id is required for update , id passed was - {}",userUpdateRequest.getId());
            throw new CustomValidationException("either UserId or Id is required for update");
        }

    }



}
