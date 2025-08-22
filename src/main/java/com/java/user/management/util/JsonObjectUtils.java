package com.java.user.management.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.user.management.vo.UserResponse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JsonObjectUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Converts a JSON string to an instance of the specified class type.
     *
     * @param jsonLocation the JSON will be read from the location passed
     * @param clazz the target class type to convert the JSON to
     *
     * @param <T> the type of the target class
     * @return an instance of the target class type
     * @throws Exception if the JSON parsing fails
     */
    public static <T> T convertToObject(String jsonLocation, Class<T> clazz) {
        try {
            URL resource = UserResponse.class.getClassLoader().getResource(jsonLocation);
            if (resource == null) {
                throw new IllegalArgumentException("File not found!");
            }

            File jsonFile = new File(resource.getFile());
            return objectMapper.readValue(jsonFile, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Converts any Java object to a JSON string.
     *
     * @param obj the Java object to be converted to JSON
     * @return a JSON string representing the object
     * logs Exception if the conversion fails
     */
    public static String toJson(Object obj)  {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<UserResponse> mapJsonToUserResponseList(String jsonLocation, Class<T> clazz) throws IOException {
            try {
                URL resource = UserResponse.class.getClassLoader().getResource(jsonLocation);
                if (resource == null) {
                    throw new IllegalArgumentException("File not found!");
                }
                File jsonFile = new File(resource.getFile());
                ObjectMapper objectMapper = new ObjectMapper();

                // Convert JSON string to List<UserResponse>
                return objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));

            } catch (IOException e) {
            throw new RuntimeException(e);
                }
    }
}
