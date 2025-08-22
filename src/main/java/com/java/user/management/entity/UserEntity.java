package com.java.user.management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

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
    private String f_Name;
    private String l_Name;
    private String name;
    private String email;
    private Date birth_Date;
    private String user_Id;
    @OneToMany(targetEntity = Address.class,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ua_fk",referencedColumnName = "id")
    private List<Address> addressList;

}
