package com.java.user.management.service;


import com.java.user.management.constants.ErrorConstants;
import com.java.user.management.entity.UserEntity;
import com.java.user.management.exceptions.CustomValidationException;
import com.java.user.management.exceptions.FieldErrorDetail;
import com.java.user.management.mappers.UserMapper;
import com.java.user.management.repository.UserRepository;
import com.java.user.management.util.CustomUtils;
import com.java.user.management.vo.Address;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${api.admin.id}")
    private String adminID;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private List<FieldErrorDetail> errors;

    public ResponseEntity<UserResponse> saveUser(UserRequest userRequest) throws ParseException {
        errors = new ArrayList<>();
        validatePermanentAddress(userRequest.getAddressList());
        validateUserExists(userRequest);
        UserEntity userEntity = userRepository.save(userMapper.userRequestToEntity(userRequest));
        return new ResponseEntity<>(userMapper.userEnityToResposne(userEntity), HttpStatus.CREATED);
    }

    void validateUserExists(UserRequest userRequest) {
        List<UserEntity> existingUsers = userRepository.findByUserIdOrFNameOrLName(userRequest.getUserId(),
                userRequest.getFirstName(),
                userRequest.getLastName());

        // Separate the checks into userId and firstName + lastName
        boolean isUserIdExists = existingUsers.stream()
                .anyMatch(user -> user.getUser_Id().equals(userRequest.getUserId()));

        boolean isFirstNameExists = existingUsers.stream()
                .anyMatch(user -> user.getF_Name().equals(userRequest.getFirstName()));
        boolean isLastNameExist = existingUsers.stream()
                .anyMatch(user -> user.getL_Name().equals(userRequest.getLastName()));
        log.info("isUserIdExists: {} :: isFirstNameExists: {} :: isLastNameExist: {}"
                ,isUserIdExists,isFirstNameExists,isLastNameExist);
        if (isUserIdExists){

            errors.add(FieldErrorDetail.builder().field("userId").code(ErrorConstants.DUPLICATE_KEY).message("User Id already Exist").build());

        }
        if(isFirstNameExists && isLastNameExist){
            errors.add(FieldErrorDetail.builder().field("firstName").code(ErrorConstants.DUPLICATE_KEY).message("User with last name already Exist").build());
            errors.add(FieldErrorDetail.builder().field("lastName").code(ErrorConstants.DUPLICATE_KEY).message("User with last name already Exist").build());

        }
        errors.stream()
                .findAny() // Checks if there is any element in the list
                .ifPresent(item -> { // If an element is present, throw the exception
                    throw new CustomValidationException(errors);
                });
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

        log.info("Admin ID from request : {}",xAdmin);
        log.info("Admin ID from YML : {}",adminID);
        if(!CustomUtils.isValidAdminId(xAdmin,adminID)){
            errors = new ArrayList<>();
            errors.add(FieldErrorDetail.builder().field("x-adminId").code(ErrorConstants.MISSING_PARAMETER).message("Non Admins cannot delete user").build());
            throw new CustomValidationException(errors);
        }
            userRepository.deleteById(id);

        return new ResponseEntity<>("Deleted user successfully with Id : "+id,HttpStatus.OK);
    }

    private void validateInputs(UserUpdateRequest userUpdateRequest) {

        if(userUpdateRequest==null
                || userUpdateRequest.getId()<=0){
            log.error("Id is required for update , id passed was - {}",userUpdateRequest.getId());
            throw new CustomValidationException(
                    List.of(
                            FieldErrorDetail.builder()
                                    .field("id")
                                    .code(ErrorConstants.MISSING_PARAMETER)
                                    .message("ID is required for Update").build()
                            )
                                                );
        }

    }

    private void validatePermanentAddress(List<Address> addressList) {

        long count = addressList.stream().filter(Address::isPermanent).count();

        if(count > 1){
            throw new CustomValidationException(
                    List.of(
                            FieldErrorDetail.builder()
                                    .field("Address.isPermanent")
                                    .code(ErrorConstants.VALIDATION_ERROR)
                                    .message("Only one permanent address is allowed.").build()
                    )
            );
        }
    }



}
