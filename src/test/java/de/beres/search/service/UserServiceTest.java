package de.beres.search.service;

import de.beres.search.entities.User;
import de.beres.search.entities.UserRepository;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void selectTest() throws Exception{

        System.out.println("selectTest()");
        Flux<User> userFlux = userService.getUser();
        userFlux.subscribe(new Subscriber<User>() {
            private Subscription s;
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(100);
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(User user)  {
                System.out.println("onNext");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch ( InterruptedException e ){System.out.println(e.getMessage());}
                System.out.println(user.toString());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
        Thread.sleep(20000);
    }

}