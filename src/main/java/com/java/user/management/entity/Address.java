package com.java.user.management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADD_TBL")
public class Address {
    @Id
    @GeneratedValue
    private int id;
    private String add_Line_One;
    private String add_Line_Two;
    private String city;
    private String state;
    private String zip;
    private String country;
    private boolean permanent;
    private boolean temp;
}
