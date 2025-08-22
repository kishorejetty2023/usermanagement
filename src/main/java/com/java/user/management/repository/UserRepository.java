package com.java.user.management.repository;


import com.java.user.management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query(value = "update USER_TBL u set u.EMAIL = :email where u.id = :id",nativeQuery = true)
    int updateEmail( @Param(value = "email") String email, @Param(value = "id") long id);

    @Query(value = "SELECT * FROM USER_TBL u WHERE u.user_Id = :userId OR u.f_Name = :fName OR u.f_Name = :lName",nativeQuery = true)
    List<UserEntity> findByUserIdOrFNameOrLName(@Param("userId") String userId,
                                                @Param("fName") String fName,
                                                @Param("lName") String lName);
}
