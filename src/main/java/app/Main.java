package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import app.Person.*;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
            try {
                // Create Person object
                Person person = Person.builder()
                        .user(new UserIdentifier("John Doe", 11))
                        .address(new Address("Main St", 123))
                        .brothers(List.of(
                                new UserIdentifier("Nikita", 21)
                        ))
                        .build();


                JDBCConnect.insertPerson(person);

                // Convert the Person object to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
                String json = writer.writeValueAsString(person);

                // Print the JSON string
                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
