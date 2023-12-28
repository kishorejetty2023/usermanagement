package com.java.user.management.service;


import com.java.user.management.entity.UserEntity;
import com.java.user.management.exceptions.CustomValidationException;
import com.java.user.management.mappers.UserMapper;
import com.java.user.management.repository.UserRepository;
import com.java.user.management.util.CustomUtils;
import com.java.user.management.vo.Address;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        log.info("Converting to SQL Date {}", CustomUtils.convetStringDateToSqlDate(userRequest.getDob()));

        validateAddress(userRequest.getAddressList());
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
            log.error("Id is required for update , id passed was - {}",userUpdateRequest.getId());
            throw new CustomValidationException("Id is required for update");
        }

    }

    private void validateAddress(List<Address> addressList) {

        long count = addressList.stream().filter(Address::isPermanent).count();

        if(count > 1){
            throw new CustomValidationException("Only one permanent address is allowed ");
        }
    }



}
