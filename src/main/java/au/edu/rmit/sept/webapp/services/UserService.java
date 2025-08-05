package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.User;

public interface UserService {
    void registerUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    void updateUser(User user);
    void deleteUser(User user);
    User authenticate(String username, String password);
    boolean verifyPassword(User user, String password);
    void changeEmail(User user, String newEmail);
    void changePassword(User user, String newPassword);
}