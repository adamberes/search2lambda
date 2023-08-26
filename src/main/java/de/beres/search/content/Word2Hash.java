package de.beres.search.content;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class Word2Hash extends String2Hash{
    HashMap<String,String> word;

    public Word2Hash() {
        this.word = new HashMap<>();
    }

    public String addWord(String string) {
        String code = this.generateHashString(string);
        word.put(string,code);
        return code;
    }
    public void logAll(){
        try {
            FileWriter fileWriter = new FileWriter("word.txt");
            for (Map.Entry<String, String> entry : word.entrySet()) {
                String s = "Key: " + entry.getKey() + " Value: " + entry.getValue();
                fileWriter.write(s + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
}
