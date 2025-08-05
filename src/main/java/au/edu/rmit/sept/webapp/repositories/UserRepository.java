package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.User;

import java.sql.SQLException;

public interface UserRepository {
    void insert(User user);
    void setup() throws SQLException;
    User findByUsername(String username);
    User findByEmail(String email);
    void update(User user);
    void delete(User user);
}