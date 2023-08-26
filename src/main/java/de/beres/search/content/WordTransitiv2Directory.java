package de.beres.search.content;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class WordTransitiv2Directory {
    //word --> hash --> filename
    public Word2Hash word2Hash = new Word2Hash();
    public Hash2Directory hash2Directory = new Hash2Directory();

    public void addWord(String string, String fileName){
        String hashCode = word2Hash.addWord(string);
        System.out.println("Word " + string);
        if(string.contentEquals("+49"))
            string = string;
        hash2Directory.addDirectory(hashCode, fileName);
    }
}
