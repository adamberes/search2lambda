package de.beres.search;

import de.beres.search.content.WordTransitiv2Directory;
import de.beres.search.operations.FileHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@Slf4j
@Service
public class FileVisitorImpl implements FileVisitor<Path> {
    @Autowired
    FileHandle fileHandle;
    @Autowired
    ConfigurationProps configurationProps;

    Settings settings;
    @Autowired
    WordTransitiv2Directory wordTransitiv2Directory;


    public void callVisitor(Settings settings){
        this.settings = settings;
        settings.getContentSearchResult().setWordTransitiv2Directory(wordTransitiv2Directory);
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        FileVisitorImpl fileVisitor = this;
        try {
            Files.walkFileTree(settings.getSrcDirectory(), fileVisitor);
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
        fileHandle.handleFile(file, settings);
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
