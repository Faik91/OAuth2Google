package net.crudapp.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    public static Map<String, Object> attributes;
    public static Set<? extends GrantedAuthority> roleSet;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        attributes = user.getAttributes();
        roleSet = (Set<? extends GrantedAuthority>) user.getAuthorities();
        return new CustomOAuth2User(user);
    }
}
