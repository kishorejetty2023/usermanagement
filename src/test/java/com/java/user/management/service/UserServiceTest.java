package com.java.user.management.service;

import com.java.user.management.entity.UserEntity;
import com.java.user.management.exceptions.CustomValidationException;
import com.java.user.management.mappers.UserMapper;
import com.java.user.management.repository.UserRepository;
import com.java.user.management.util.CustomUtils;
import com.java.user.management.util.JsonObjectUtils;
import com.java.user.management.vo.Address;
import com.java.user.management.vo.UserRequest;
import com.java.user.management.vo.UserResponse;
import com.java.user.management.vo.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;
    private UserEntity userEntity;
    private UserResponse userResponse;
    private UserUpdateRequest userUpdateRequest;
    int idd;
    long longId;
    String email;
    String xAdminId, adminId,  userId, fName,lName;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(userService, "adminID", "e1482313-aea9-4029-9214-505fce95e8bf");
        // Sample test data
        userRequest = JsonObjectUtils
                .convertToObject("data/UserRequest.json",UserRequest.class);

        userEntity = new UserEntity();
        userEntity.setUser_Id("U123");
        userEntity.setF_Name("John");
        userEntity.setL_Name("Doe");

        userResponse = JsonObjectUtils.convertToObject(
                "data/UserResponse.json", UserResponse.class) ;

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1);
        userUpdateRequest.setEmailId("newemail@example.com");
        longId = 1L;
        email="newemail@example.com";
        idd=1;
        xAdminId = "ZTE0ODIzMTMtYWVhOS00MDI5LTkyMTQtNTA1ZmNlOTVlOGJm";
        adminId = "e1482313-aea9-4029-9214-505fce95e8bf";
        userId = "A1234";
        fName = "firstName";
        lName = "lastName";

    }

    @Test
    void testSaveUser_Success() throws ParseException {
        // Mock repository and mapper methods
        when(userRepository.findByUserIdOrFNameOrLName(userRequest.getUserId(), userRequest.getFirstName(), userRequest.getLastName())).thenReturn(List.of());
        when(userMapper.userRequestToEntity(userRequest)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEnityToResposne(userEntity)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userService.saveUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).userRequestToEntity(userRequest);
        verify(userMapper, times(1)).userEnityToResposne(userEntity);
    }

    @Test
    void testSaveUser_ThrowsValidationException() {
        //Arrange : Make sure that there is some error in the request
        List<Address> updatedAddresses = userRequest.getAddressList().stream()
                .map(address -> {
                    address.setPermanent(true);
                    return address;
                })
                .limit(2) // Ensure only first two addresses are updated
                .collect(Collectors.toList());

        userRequest.setAddressList(updatedAddresses);

        // Expecting validation exception
        CustomValidationException exception = assertThrows(CustomValidationException.class, 
            () -> userService.saveUser(userRequest));

        assertNotNull(exception);
        assertEquals(1, exception.getErrors().size());
        verify(userRepository, times(0)).findByUserIdOrFNameOrLName(userId, fName, lName);
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(idd)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEnityToResposne(userEntity)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userService.getUserById(idd);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(2)).findById(idd);
        verify(userMapper, times(2)).userEnityToResposne(userEntity);
    }

    @Test
    void testDeleteUserById_Success() {
        try (MockedStatic<CustomUtils> mockedUtils = mockStatic(CustomUtils.class)) {
            mockedUtils.when(() -> CustomUtils.isValidAdminId(xAdminId, adminId)).thenReturn(true);

            doNothing().when(userRepository).deleteById(1);

            ResponseEntity<String> response = userService.deleteUserById(1, "ZTE0ODIzMTMtYWVhOS00MDI5LTkyMTQtNTA1ZmNlOTVlOGJm");

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(userRepository, times(1)).deleteById(eq(1));
        }
    }

    @Test
    void testDeleteUserById_ThrowsExceptionForNonAdmin() {
        try (MockedStatic<CustomUtils> mockedUtils = mockStatic(CustomUtils.class)) {
            mockedUtils.when(() -> CustomUtils.isValidAdminId(xAdminId, adminId)).thenReturn(false);

            // Expecting validation exception
            CustomValidationException exception = assertThrows(CustomValidationException.class,
                    () -> userService.deleteUserById(1, "InvalidAdminID"));

            assertNotNull(exception);
            assertEquals(1, exception.getErrors().size());
            verify(userRepository, times(0)).deleteById(eq(1));
        }
    }

    @Test
    void testUpdateUserEmailByLogIdOrId_Success() {
        when(userRepository.updateEmail(email, longId)).thenReturn(1);

        ResponseEntity<String> response = userService.updateUserEmailByLogIdOrId(userUpdateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userRepository, times(1)).updateEmail(email, longId);
    }

    @Test
    void testUpdateUserEmailByLogIdOrId_NoRecordFound() {
        when(userRepository.updateEmail(email, longId)).thenReturn(0);

        ResponseEntity<String> response = userService.updateUserEmailByLogIdOrId(userUpdateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("No records found with Id: 1", response.getBody());
        verify(userRepository, times(1)).updateEmail(email, longId);
    }

    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.map(anyList())).thenReturn(List.of(userResponse));

        ResponseEntity<List<UserResponse>> response = userService.getAllUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).map(anyList());
    }
}
