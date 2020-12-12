package net.crudapp.service;

import net.crudapp.model.AuthenticationProvider;
import net.crudapp.model.Role;
import net.crudapp.model.User;
import net.crudapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User getUserByName(String username) {
        return userRepository.getUserByEmail(username);
    }

    public List<User> allUsers() {
        return this.userRepository.findAll();
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public void updateUser(User user) {
        this.userRepository.save(user);
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id).get();
    }


    public void createNewUserAfterOAuth2LoginSuccess(String email, String firstName, String lastName, AuthenticationProvider provider) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAuthProvider(provider);

        userRepository.save(user);
    }
}
