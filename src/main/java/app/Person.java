package app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private UserIdentifier user;
    private List<UserIdentifier> brothers;
    private Address address;

    @Data
    @AllArgsConstructor
    @Builder
    public static class UserIdentifier {

        private String name;
        private int age;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Address {

        private String street;
        @JsonProperty("building_number")
        private int buildingNumber;
    }
}
