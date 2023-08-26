package de.beres.search.content;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class String2Hash {
    public String generateHashString(String key){
        return Hashing.sha256().hashString(key, StandardCharsets.UTF_8).toString();
    }

}
