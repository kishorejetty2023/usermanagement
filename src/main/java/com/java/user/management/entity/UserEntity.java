package com.java.user.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TBL", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}),
        @UniqueConstraint(columnNames = {"userId"})
        })
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fName;
    private String lName;
    private String name;
    private String email;
    private Date birthDate;
    private String userId;

}
