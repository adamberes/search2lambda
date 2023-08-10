package de.beres.search;

import de.beres.search.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class ControllerDir {

    @Autowired
    ServiceDir serviceDir;
    @Autowired
    UserService userService;

    @GetMapping("/do")
    String work() throws IOException {
        serviceDir.work();
        return "OK";
    }

}
