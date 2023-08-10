package de.beres.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
public class ServiceDir {

    @Autowired
    FileVisitorImpl fileVisitor;

    public void work() {
        Path startDir = Paths.get("C:\\bin");
            fileVisitor.callVisitor(startDir);
    }
}
