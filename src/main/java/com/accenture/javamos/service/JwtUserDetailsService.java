package com.accenture.javamos.service;

import com.accenture.javamos.entity.Role;
import com.accenture.javamos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userService.findUserByEmail(userName);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(userName);
        }
        List<GrantedAuthority> authorities = getUserAuthority(user.get().getRoles());
        return buildUserForAuthentication(user.get(), authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        final boolean ACCOUNT_NON_EXPIRED = true;
        final boolean CREDENTIALS_NON_EXPIRED = true;
        final boolean ACCOUNT_NON_LOCKED = true;

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getActive(), ACCOUNT_NON_EXPIRED, CREDENTIALS_NON_EXPIRED, ACCOUNT_NON_LOCKED, authorities);
    }
}
