package app;

import java.sql.*;

public class JDBCConnect {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pass";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to PostgreSQL: " + e.getMessage());
        }
        return connection;
    }


    public static void insertPerson(Person person) {
        String personSql = "INSERT INTO person (name, age, street, building_number) VALUES (?, ?, ?, ?) RETURNING id";
        String brothersSql = "INSERT INTO brothers (person_id, name, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement personStatement = connection.prepareStatement(personSql);
             PreparedStatement brothersStatement = connection.prepareStatement(brothersSql)) {

            // Insert person
            personStatement.setString(1, person.getUser().getName());
            personStatement.setInt(2, person.getUser().getAge());
            personStatement.setString(3, person.getAddress().getStreet());
            personStatement.setInt(4, person.getAddress().getBuildingNumber());

            ResultSet personResultSet = personStatement.executeQuery();
            if (personResultSet.next()) {
                int personId = personResultSet.getInt("id");
                System.out.println("A new person was inserted successfully with ID: " + personId);

                // Insert brothers
                for (Person.UserIdentifier brother : person.getBrothers()) {
                    brothersStatement.setInt(1, personId);
                    brothersStatement.setString(2, brother.getName());
                    brothersStatement.setInt(3, brother.getAge());
                    brothersStatement.addBatch();
                }
                int[] rowsInserted = brothersStatement.executeBatch();
                System.out.println(rowsInserted.length + " brothers were inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}