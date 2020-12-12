package net.crudapp.controller;

import net.crudapp.model.User;
import net.crudapp.service.RoleService;
import net.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(value = "/all-users")
    public List<Object> getAllUsers() {
        List<Object> usersAndRoles = new ArrayList<>();
        usersAndRoles.add(userService.allUsers());
        usersAndRoles.add(roleService.allRoles());

        return usersAndRoles;
    }

    @PostMapping(value = "admin/update-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) {

        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.getUserById(user.getId()).getPassword());
        }
        this.userService.updateUser(user);
        return user;
    }

    @PostMapping(value = "admin/add-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user) {
        this.userService.saveUser(user);
        return user;
    }

    @PostMapping(value = "admin/delete-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestBody long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.ok("The user is deleted");
    }
}
