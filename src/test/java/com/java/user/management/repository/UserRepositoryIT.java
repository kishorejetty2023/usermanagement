package com.java.user.management.repository;


import com.java.user.management.configuration.DBConfig;
import com.java.user.management.entity.Address;
import com.java.user.management.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@DataJpaTest
@Import(DBConfig.class)
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUpdateEmail() {
        // Arrange
        // Create sample addresses
        Address address1 = new Address();
        address1.setAdd_Line_One("add 1");
        address1.setAdd_Line_Two("add 2");
        address1.setCity("City A");
        address1.setState("StateA");
        address1.setZip("12345");
        address1.setCountry("USA");
        address1.setPermanent(false);
        address1.setTemp(true);
        //Address address2 = new Address(2,"add 12","add 22","City 2A" ,"StateB", "12345","USA", true,true);

        // Create and populate the UserEntity object
        UserEntity user = new UserEntity();
        user.setId(234);
        user.setF_Name("John");
        user.setL_Name("Doe");
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");

        // Set birth_Date using java.sql.Date
        user.setBirth_Date(Date.valueOf("1990-01-01")); // YYYY-MM-DD format

        user.setUser_Id("U001");

//        List<Address> addressList = new ArrayList<>();
//        addressList.add(address1);
        //addressList.add(address2);
        user.setAddressList(List.of(address1));
        UserEntity rt = userRepository.save(user);
        userRepository.flush();
       // entityManager.flush();
        log.info("entity info : {}",rt);
        // Act
        int updatedCount = userRepository.updateEmail("new@example.com", 1);
        userRepository.flush();
        // Assert
        assertThat(updatedCount).isEqualTo(1);
        UserEntity updatedUser = userRepository.findById(1).orElseThrow();
        log.info("entity info after update : {}",updatedUser);
        assertThat(updatedUser.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    void testFindByUserIdOrFNameOrLName() {
        // Arrange
        UserEntity user1 = new UserEntity();
        user1.setUser_Id("U001");
        user1.setF_Name("John");
        user1.setL_Name("Doe");
        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUser_Id("U002");
        user2.setF_Name("Jane");
        user2.setL_Name("Smith");
        userRepository.save(user2);

        // Act
        List<UserEntity> users = userRepository.findByUserIdOrFNameOrLName("U001", "Jane", "Doe");

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users).extracting("user_Id").containsExactlyInAnyOrder("U001", "U002");
    }
}
