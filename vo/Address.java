package com.java.user.management.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @JsonIgnore
    private String id;
    private String addLineOne;
    private String addLineTwo;
    private String city;
    private String state;
    private String zip;
    private String country;
    private boolean permanent;
    private boolean temp;

}
