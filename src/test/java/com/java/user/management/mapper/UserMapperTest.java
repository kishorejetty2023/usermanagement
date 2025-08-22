package com.java.user.management.mapper;

import com.java.user.management.entity.Address;
import com.java.user.management.entity.UserEntity;
import com.java.user.management.mappers.UserMapper;
import com.java.user.management.util.CustomUtils;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testUserEntityToResponse() {
        // Prepare data
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setF_Name("John");
        userEntity.setL_Name("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setUser_Id("user123");
        try {
            userEntity.setBirth_Date(CustomUtils.convertStringDateToSqlDate("11-10-1980"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        userEntity.setAddressList(Arrays.asList(new Address(1,"Street 1", "Street 2", "City", "State", "12345", "Country", true, false)));

        // Perform mapping
        UserResponse userResponse = userMapper.userEnityToResposne(userEntity);

        // Verify result
        assertNotNull(userResponse);
        assertEquals("John", userResponse.getFirstName());
        assertEquals("Doe", userResponse.getLastName());
        assertEquals("john.doe@example.com", userResponse.getEmailId());
        assertEquals("user123", userResponse.getUserId());
        assertEquals("11-10-1980", userResponse.getDob());
        assertNotNull(userResponse.getAddressList());
        assertEquals(1, userResponse.getAddressList().size());
    }

    @Test
    void testUserRequestToEntity() throws ParseException {
        // Prepare data
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmailId("john.doe@example.com");
        userRequest.setUserId("user123");
        userRequest.setDob("11-10-1980");

        userRequest.setAddressList(List.of(new com.java.user.management.vo.Address("1", "Street 1", "Street 2", "City", "State", "12345", "Country", true, false)));

        // Perform mapping
        UserEntity userEntity = userMapper.userRequestToEntity(userRequest);

        // Verify result
        assertNotNull(userEntity);
        assertEquals("John", userEntity.getF_Name());
        assertEquals("Doe", userEntity.getL_Name());
        assertEquals("john.doe@example.com", userEntity.getEmail());
        assertEquals("user123", userEntity.getUser_Id());
        assertEquals(Date.valueOf("1980-11-10"), userEntity.getBirth_Date());
        assertNotNull(userEntity.getAddressList());
        assertEquals(1, userEntity.getAddressList().size());
    }

    @Test
    void testMapListUserEntitiesToResponses() {
        // Prepare data
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1);
        userEntity1.setF_Name("John");
        userEntity1.setL_Name("Doe");
        userEntity1.setEmail("john.doe@example.com");
        userEntity1.setUser_Id("user123");
        try {
            userEntity1.setBirth_Date(CustomUtils.convertStringDateToSqlDate("11-10-1980"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2);
        userEntity2.setF_Name("Jane");
        userEntity2.setL_Name("Smith");
        userEntity2.setEmail("jane.smith@example.com");
        userEntity2.setUser_Id("user456");
        try {
            userEntity2.setBirth_Date(CustomUtils.convertStringDateToSqlDate("11-10-1980"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<UserResponse> responses = userMapper.map(Arrays.asList(userEntity1, userEntity2));

        // Verify result
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("John", responses.get(0).getFirstName());
        assertEquals("Jane", responses.get(1).getFirstName());
    }

    @Test
    void testRequestToEntityAddressMapping() {
        // Prepare data
        com.java.user.management.vo.Address addressVO = new com.java.user.management.vo.Address(
                "1","Street 1", "Street 2", "City", "State", "12345", "Country", true, false);

        // Perform mapping
        Address address = userMapper.requesttoEntityAddress(addressVO);

        // Verify result
        assertEquals("Street 1", address.getAdd_Line_One());
        assertEquals("Street 2", address.getAdd_Line_Two());
        assertEquals("City", address.getCity());
        assertEquals("State", address.getState());
        assertEquals("12345", address.getZip());
        assertEquals("Country", address.getCountry());
        assertTrue(address.isPermanent());
        assertFalse(address.isTemp());
    }

    @Test
    void testEntityAddressToResponseAddressMapping() {
        // Prepare data
        Address address = new Address(1,"Street 1", "Street 2", "City", "State", "12345", "Country", true, false);

        // Perform mapping
        com.java.user.management.vo.Address addressVO = userMapper.entityAddressToResponseAddress(address);

        // Verify result
        assertNotNull(addressVO);
        assertEquals("Street 1", addressVO.getAddLineOne());
        assertEquals("Street 2", addressVO.getAddLineTwo());
        assertEquals("City", addressVO.getCity());
        assertEquals("State", addressVO.getState());
        assertEquals("12345", addressVO.getZip());
        assertEquals("Country", addressVO.getCountry());
        assertTrue(addressVO.isPermanent());
        assertFalse(addressVO.isTemp());
    }

    @Test
    void testMapListAddresses() {
        // Prepare data

        com.java.user.management.vo.Address addressVO1 = new com.java.user.management.vo.Address("1","Street 1", "Street 2", "City", "State", "12345", "Country", true, false);
        com.java.user.management.vo.Address addressVO2 = new com.java.user.management.vo.Address("2","Street 3", "Street 4", "Town", "Province", "67890", "Country", false, true);

        List<Address> addresses = userMapper.mapListAddresses(Arrays.asList(addressVO1, addressVO2));

        // Verify result
        assertEquals(2, addresses.size());
        assertEquals("City", addresses.get(0).getCity());
        assertEquals("Town", addresses.get(1).getCity());
    }

    @Test
    void testMapEntityListAddresses() {
        // Prepare data
        Address address1 = new Address(1,"Street 1", "Street 2", "City", "State", "12345", "Country", true, false);
        Address address2 = new Address(2,"Street 3", "Street 4", "Town", "Province", "67890", "Country", false, true);

        List<com.java.user.management.vo.Address> addressVOs = userMapper.mapEtityListAddresses(Arrays.asList(address1, address2));

        // Verify result
        assertNotNull(addressVOs);
        assertEquals(2, addressVOs.size());
        assertEquals("City", addressVOs.get(0).getCity());
        assertEquals("Town", addressVOs.get(1).getCity());
    }
}
