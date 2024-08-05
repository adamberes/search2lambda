package de.beres.search.entities;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
//    @Override
//    default Flux<User> findAll() {
//        return null;
//    }
}
