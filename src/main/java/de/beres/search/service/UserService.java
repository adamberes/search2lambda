package de.beres.search.service;

import de.beres.search.entities.User;
import de.beres.search.entities.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    final private UserRepository userRepository;

    public Flux<User> getUser(){
        return userRepository.findAll();
    }

}
