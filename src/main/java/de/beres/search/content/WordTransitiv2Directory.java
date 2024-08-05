package de.beres.search.content;


import com.google.common.hash.HashCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class WordTransitiv2Directory {
    //word --> hash --> filename
    public Word2Hash word2Hash = new Word2Hash();
    public Hash2Directory hash2Directory = new Hash2Directory();

    public void addWord(String string, String fileName){
        String hashCode = word2Hash.generateHashAddWord(string);
        log.info("Word [" + string + "] hash [" + hashCode + "] file [" + fileName + "]" + string);
        if(string.contentEquals("+49"))
            string = string;
        if(hashCode.length()!=0)
            hash2Directory.addDirectory(hashCode, fileName);
    }
    public Bag<String> getDirList4Word(String string){
        String keyFromWord = word2Hash.getWord().get(string);
        Bag<String> stringList;
        stringList = hash2Directory.getDirList(keyFromWord);
        return stringList;
    }
    public void writeWord2File(String fileName){
        try (FileWriter fileWriter = new FileWriter(fileName)){
        for (Map.Entry<String, String> entry : word2Hash.getWord().entrySet()) {
            String s = "Key: " + entry.getKey() + " Value: " + entry.getValue();
            fileWriter.write(s + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }catch(
    IOException e){
        log.error(e.getMessage());
    }
}
    public void readWordFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                List<String> splitList = Arrays.asList(line.split(" "));
                String key = splitList.get(1);
                String value = splitList.get(3);
                word2Hash.getWord().put(splitList.get(1), splitList.get(3));
            }
        } catch (
                IOException e) {
            log.error(e.getMessage());
        }
    }
    public void readFileNameFromFile(String fileName) {
        Bag<String> dirNew = null;
        String hashCode = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            boolean present = false;
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                if(line.startsWith("HashCode")){
                    hash2Directory.hash2Directory.put(hashCode,dirNew);
                    present = true;
                    dirNew = null;
                }
                if(present){
                    dirNew = null;
                    present = false;
                }
                if(line.startsWith("HashCode from Word: ")){
                    hashCode = line.substring("HashCode from Word: ".length());
                }
                if(line.startsWith("File: ")){
                    line = line.substring("File: ".length());
                    if(dirNew==null){
                        dirNew = new HashBag<>();
                        dirNew.add(line);
                    }else{
                        dirNew.add(line);
                    }
                }
            }
            int i = 0;
            i = 9;
            } catch (
                IOException e) {
            log.error(e.getMessage());
        }
    }
    public void writeFileName2File(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)){
            for (Map.Entry<String, Bag<String>> bagEntry : hash2Directory.getHash2Directory().entrySet()) {
                String s = "HashCode from Word: " + bagEntry.getKey();
                log.info(s);
                fileWriter.write(s + "\n");
                try {
                    for (String entry : bagEntry.getValue()) {
                        String s1 = "File: " + entry;
                        fileWriter.write(s1 + "\n");
                    }
                } catch (NullPointerException e) {
                    log.error(e.getMessage());
                }
            }
            fileWriter.flush();
            fileWriter.close();
        } catch ( IOException e) {
            log.error(e.getMessage());
        }
    }
    List<String> getWordList(String searchFor){
        List<String> resultList = new ArrayList<>();

        Bag<String> dirlist = getDirList4Word(searchFor);
        if(dirlist == null) {
            resultList.add("Keine Ergebnisse0");
            return resultList;
        }
        for(Iterator i = dirlist.iterator(); i.hasNext();){
            String s = i.next() + "\n";
            resultList.add(s);
        }
        if(resultList.size()==0)
            resultList.add("Keine Ergebnisse1");
        return resultList;
    }
}
