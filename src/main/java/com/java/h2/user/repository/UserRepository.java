package com.java.h2.user.repository;

import com.java.h2.user.entity.UserEntity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query(value = "update USER_TBL u set u.EMAIL = :email where u.id = :id",nativeQuery = true)
    int updateEmail( @Param(value = "email") String email, @Param(value = "id") long id);
}
