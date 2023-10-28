package de.beres.search.content;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class Word2Hash extends String2Hash{
    HashMap<String,String> word;

    public Word2Hash() {
        this.word = new HashMap<>();
    }

    public String generateHashAddWord(String string) {
        string = string.replaceAll("\r|\n|\\u00a0|\\u005c|\\u0022|\\u0027",     "");
        if(string.length()<=1 ) {
            return "";
        }
        String code = this.generateHashString(string);
        word.put(string,code);
        return code;
    }
}
