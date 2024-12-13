package com.ritik.security.service;

import com.ritik.security.model.UserPrincipal;
import com.ritik.security.model.Users;
import com.ritik.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    }

    public Users save(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}