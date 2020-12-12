package net.crudapp.controller;

import net.crudapp.model.Role;
import net.crudapp.model.User;
import net.crudapp.oauth2.CustomOAuth2UserService;
import net.crudapp.service.RoleService;
import net.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "userData";
    }

    @GetMapping("/")
    public String userOAuth2Page(@AuthenticationPrincipal User user, Model model) {

        if (user == null) {
            user = new User();
            oauth2User(user);
        }
        model.addAttribute("user", user);
        return "/userData";
    }

    public void oauth2User(User oauth2User) {

        Map<String, Object> attributes = CustomOAuth2UserService.attributes;
        String email = (String) attributes.get("email");
        User userForDatabase = userService.getUserByEmail(email);
        oauth2User.setId(userForDatabase.getId());
        oauth2User.setEmail(userForDatabase.getEmail());
        oauth2User.setFirstName(userForDatabase.getFirstName());
        oauth2User.setLastName(userForDatabase.getLastName());
        oauth2User.setAuthProvider(userForDatabase.getAuthProvider());

        //got the role incoming OAuth2GoogleUser
        Set<? extends GrantedAuthority> authorities = CustomOAuth2UserService.roleSet;
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(authorities.iterator().next().toString()));
        oauth2User.setRoles(roleSet);
    }

}
