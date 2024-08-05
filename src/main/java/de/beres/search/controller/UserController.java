package de.beres.search.controller;

import de.beres.search.entities.User;
import de.beres.search.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Slf4j
@RequestMapping(value = "api/")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("users")
    public Flux<User> getUsers() throws InterruptedException{
        long start = System.currentTimeMillis();
        Flux<User> userFlux = userService.getUser();
        long end = System.currentTimeMillis();
        Long time = Long.valueOf(end-start);
        log.info("time: " + Long.valueOf(time));
        return this.userService.getUser();
    }
}
