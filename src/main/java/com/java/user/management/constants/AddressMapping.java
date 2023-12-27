package com.java.user.management.constants;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Address {
    private String street;
    private String city;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class MappedAddress {
    private String mappedStreet;
    private String mappedCity;

}

public class AddressMapping {
    public static void main(String[] args) {
        // Sample list of addresses
        List<Address> addressList = Arrays.asList(
                new Address("123 Main St", "City1"),
                new Address("456 Oak St", "City2"),
                new Address("789 Pine St", "City3")
        );

        // Use stream to map values from Address to MappedAddress
        List<MappedAddress> mappedAddressList = addressList.stream()
                .map(address -> new MappedAddress(address.getStreet(), address.getCity()))
                .toList();

        // Print the mapped addresses
        mappedAddressList.forEach(System.out::println);
    }
}
