package de.beres.search;

import de.beres.search.content.WordTransitiv2Directory;
import de.beres.search.operations.Operation;
import lombok.NonNull;
import org.apache.commons.collections4.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class ControllerDir {

    @Autowired
    ServiceDir serviceDir;
    @Autowired
    WordTransitiv2Directory wordTransitiv2Directory;
    @Autowired
    ContentSearchResult contentSearchResult;
    Settings settings;

//    @GetMapping("/abcd")
//    String work() throws IOException {
//        //Path startDir  = Paths.get("F:\\Saved2023");
//        Path startDir  = Paths.get("C:\\bin\\1Stick2023-2");
//        //Path startDir  = Paths.get("C:\\bin\\ProjektReact\\React2");
//
//
//        Path targetDir = Paths.get("C:\\bin\\BilderZiel");
//        settings = Settings.builder()
//                .js(Boolean.valueOf(true)).jsx(Boolean.valueOf(true))
//                .docx(Boolean.valueOf(true)).pdf(Boolean.valueOf(true))
//                .jpg(Boolean.valueOf(false)).nef(Boolean.valueOf(false))
//                .srcDirectory(startDir)
//                .operation(Operation.INDEX)// 1 copy; 2 mirror; 3 index;
//                .contentSearchResult(new ContentSearchResult())
//                .destDirectory(targetDir).build();
//
//        serviceDir.work(settings);
//        settings.getContentSearchResult().getWordTransitiv2Directory().word2Hash.logAll();
//        settings.getContentSearchResult().getWordTransitiv2Directory().hash2Directory.logAll();
//        return "OK";
//    }
    @PostMapping(value = "/do")
    String work2(@RequestBody Data data) throws IOException {

        System.out.println(data.getSrcDir());
        System.out.println(data.getDstDir());
        System.out.println(data.getMode());
        //Path startDir  = Paths.get("C:\\bin\\1Stick2023-2");
        //Path startDir  = Paths.get("C:\\bin\\1Stick2023-1");
        Path startDir  = Paths.get("C:\\bin\\1Stick2023");

        Path targetDir = Paths.get("C:\\bin\\BilderZiel");
        settings = Settings.builder()
                .js(Boolean.valueOf(true)).jsx(Boolean.valueOf(true))
                .docx(Boolean.valueOf(true)).pdf(Boolean.valueOf(true))
                .jpg(Boolean.valueOf(false)).nef(Boolean.valueOf(false))
                .srcDirectory(startDir)
                .operation(Operation.INDEX)// 1 copy; 2 mirror; 3 index;
                .contentSearchResult(contentSearchResult)
                .destDirectory(targetDir).build();

        serviceDir.work(settings);
        settings.getContentSearchResult().getWordTransitiv2Directory().writeWord2File("word.txt");
        settings.getContentSearchResult().getWordTransitiv2Directory().writeFileName2File("word-hash2Directory.txt");
        return data.getSrcDir();
    }
    @PostMapping("/list")
    List<String> getDirList(@RequestBody SearchText searchText){
        List<String> stringList = new ArrayList<>();
        System.out.println(searchText.getSearchText());

        //List<String> returnvalue = Stream.iterate(1, i -> i + 1).limit(10).map(i -> i + " " + searchText.getSearchText() + "<br/>").collect(Collectors.toList());
        Bag<String> dirlist = wordTransitiv2Directory.getDirList4Word(searchText.getSearchText());
        if(dirlist == null) {
            stringList.add("Keine Ergebnisse0");
            return stringList;
        }
        for(Iterator i =  dirlist.iterator();   i.hasNext();){
            String s = i.next() + "\n";
            stringList.add(s);
        }
        if(stringList.size()==0)
            stringList.add("Keine Ergebnisse1");
        return stringList;
    }
    @GetMapping("/read")
    String getDirListRead(){
        settings = Settings.builder()
                .contentSearchResult(contentSearchResult).build();
        settings.getContentSearchResult().getWordTransitiv2Directory().readWordFromFile("word.txt");
        settings.getContentSearchResult().getWordTransitiv2Directory().readFileNameFromFile("word-hash2Directory.txt");

        return "Done";
    }
}
