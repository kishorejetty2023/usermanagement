package com.java.user.management.mappers;


import com.java.user.management.entity.Address;
import com.java.user.management.entity.UserEntity;

import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import org.mapstruct.*;

import java.text.ParseException;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {



    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "f_Name")
    @Mapping(target = "lastName", source = "l_Name")
    @Mapping(target = "emailId", source = "email")
    @Mapping(target = "userId", source = "user_Id")
    @Mapping(target = "dob", expression = "java(com.java.user.management.util.CustomUtils.convertSqlDateToDateString(userEntity.getBirth_Date()))")
    @Mapping(target="addressList", source = "addressList")
    UserResponse userEnityToResposne(UserEntity userEntity);

//    @AfterMapping
//    default void userEnityToResposne(@MappingTarget UserResponse userResponse,UserEntity userEntity){
//            userResponse.setDob(CustomUtils.convertSqlDateToDateString(userEntity.getBirth_Date()));
//   }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "f_Name", source = "firstName")
    @Mapping(target = "l_Name", source = "lastName")
    @Mapping(target = "name", expression = "java(userRequest.getFirstName()+\" \"+userRequest.getLastName())")
    @Mapping(target = "email", source = "emailId")
    @Mapping(target = "user_Id", source = "userId")
    @Mapping(target = "birth_Date", expression = "java(com.java.user.management.util.CustomUtils.convertStringDateToSqlDate(userRequest.getDob()))")
    @Mapping(target="addressList", source = "addressList")
    UserEntity userRequestToEntity(UserRequest userRequest) throws ParseException;


    List<UserResponse> map(List<UserEntity> userEntities);


    @Mapping(target = "add_Line_One", source = "addLineOne")
    @Mapping(target = "add_Line_Two", source = "addLineTwo")
    @Mapping(target = "city",       source = "city")
    @Mapping(target = "state",      source = "state")
    @Mapping(target = "zip",        source = "zip")
    @Mapping(target = "country",    source = "country")
    @Mapping(target = "permanent",    source = "permanent")
    @Mapping(target = "temp",    source = "temp")
    Address requesttoEntityAddress(com.java.user.management.vo.Address address);


    List<Address> mapListAddresses(List<com.java.user.management.vo.Address> address);


    @Mapping(source = "add_Line_One", target = "addLineOne")
    @Mapping(source = "add_Line_Two", target = "addLineTwo")
    @Mapping(source = "city",       target = "city")
    @Mapping(source = "state",      target = "state")
    @Mapping(source = "zip",        target = "zip")
    @Mapping(source = "country",    target = "country")
    @Mapping(source = "permanent",    target = "permanent")
    @Mapping(source = "temp",    target = "temp")
    com.java.user.management.vo.Address entityAddressToResponseAddress(Address address);

    List<com.java.user.management.vo.Address> mapEtityListAddresses(List<Address> address);
}
