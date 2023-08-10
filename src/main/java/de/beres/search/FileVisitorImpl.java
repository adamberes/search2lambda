package de.beres.search;

import de.beres.search.operations.FileHandle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@Service
@Slf4j
//@Configurable
@AllArgsConstructor
public class FileVisitorImpl implements FileVisitor<Path> {
    @Autowired
    private FileHandle fileHandle;

    public void callVisitor(Path startDir){
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        FileVisitorImpl fileVisitor = this;
        try {
            Files.walkFileTree(startDir, fileVisitor);
        }catch (IOException e){
            log.debug("Exception: " + e.getMessage());
        }

    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        log.error("pre " + dir.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        log.error("visit " + file.toString());
        fileHandle.handleFile(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        log.info("failed " + file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        log.info("post " + dir.toString());
        return FileVisitResult.CONTINUE;
    }
}
