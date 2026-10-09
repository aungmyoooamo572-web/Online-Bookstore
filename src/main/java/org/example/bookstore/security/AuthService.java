package org.example.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleDao roleDao;

    public String login(String username,String password){
        var auth = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication =authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "%s successfully logged in.".formatted(authentication.getName());
    }

    public boolean existsByUserNameOrEmail(String username){
        User user=userDao.findByUsernameAndEmail(username)
                .orElse(null);
        return Objects.nonNull(user);
    }

    public void register(String username,String email,String password){
        var user =new User(username,passwordEncoder.encode(password),email);
        roleDao.findByRoleName("ROLE_USER")
                .ifPresentOrElse(r -> {
                    user.addRole(r);
                    userDao.save(user);
                },()->{
                    Role role =new Role();
                    role.setRoleName("ROLE_USER");
                    user.addRole(roleDao.save(role));
                    userDao.save(user);
                });
    }
}
