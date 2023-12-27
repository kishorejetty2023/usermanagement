package com.java.user.management.mappers;


import com.java.user.management.entity.UserEntity;
import com.java.user.management.util.CustomUtils;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.text.ParseException;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "FName")
    @Mapping(target = "lastName", source = "LName")
    @Mapping(target = "emailId", source = "email")
    @Mapping(target = "userId", source = "userId")
    UserResponse userEnityToResposne(UserEntity userEntity);

    @AfterMapping
    default void userEnityToResposne(@MappingTarget UserResponse userResponse,UserEntity userEntity){
            userResponse.setDob(CustomUtils.convertSqlDateToDateString(userEntity.getBirthDate()));
    }


    @Mapping(target = "FName", source = "firstName")
    @Mapping(target = "LName", source = "lastName")
    @Mapping(target = "email", source = "emailId")
    @Mapping(target = "userId", source = "userId")
    UserEntity userRequestToEntity(UserRequest userRequest) throws ParseException;

    @AfterMapping
    default void userRequestToEntity( @MappingTarget UserEntity userEntity, UserRequest userRequest) throws ParseException {
            userEntity.setName(userRequest.getFirstName()+" "+userRequest.getLastName());
            userEntity.setBirthDate(CustomUtils.convetStringDateToSqlDate(userRequest.getDob()));
    }

    List<UserResponse> map(List<UserEntity> userEntities);

}
