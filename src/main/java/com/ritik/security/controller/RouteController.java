package com.ritik.security.controller;

import com.ritik.security.model.Users;
import com.ritik.security.service.MyUserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteController {
    private final MyUserDetailsService myUserDetailsService;

    public RouteController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping
    public String getRoute() {
        return "Hello World";
    }

    @PostMapping
    public Users saveUsers(@RequestBody Users user) {
        return myUserDetailsService.save(user);
    }

    @GetMapping("/users")
    public List<Users> getUsers() {
        return myUserDetailsService.findAll();
    }
}
