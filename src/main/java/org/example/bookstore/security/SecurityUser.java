package org.example.bookstore.security;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class SecurityUser implements UserDetails {
    private final User user;
    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .toList();
    }

    @Override
    public @NullMarked String getPassword() {
        return user.getPassword();
    }

    @Override
    public @NullMarked String getUsername() {
        return user.getUsername();
    }
}
