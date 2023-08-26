package de.beres.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
class ControllerDir {

    @Autowired
    ServiceDir serviceDir;
    Settings settings;

    @GetMapping("/do")
    String work() throws IOException {
        Path startDir  = Paths.get("C:\\bin\\1Stick2023");
        Path targetDir = Paths.get("C:\\bin\\BilderZiel");
        settings = Settings.builder()
                .docx(Boolean.valueOf(true)).pdf(Boolean.valueOf(false))
                .jpg(Boolean.valueOf(false)).nef(Boolean.valueOf(false))
                .srcDirectory(startDir)
                .operation(Integer.valueOf(1))// 1 copy; 2 mirror; 3 index;
                .contentSearchResult(new ContentSearchResult())
                .destDirectory(targetDir).build();

        serviceDir.work(settings);
        settings.getContentSearchResult().getWordTransitiv2Directory().word2Hash.logAll();
        settings.getContentSearchResult().getWordTransitiv2Directory().hash2Directory.logAll();
        return "OK";
    }

}
