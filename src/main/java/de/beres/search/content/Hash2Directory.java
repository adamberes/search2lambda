package de.beres.search.content;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Hash2Directory extends String2Hash{
    Bag<String>  directorys;
    HashMap<String,Bag<String>> hash2Directory;

    public Hash2Directory() {
        this.hash2Directory =  new HashMap<>();
    }

    public void addDirectory(String hashCode, String fileName) {
        Bag<String> dir = hash2Directory.get(hashCode);
        Bag<String> dirNew = null;
        boolean isPresent=false;

        if(hashCode.equals("90d6a99fcec125ef6b61949aad4db95db6a33fa5b72ce7ea0b528ad3aaca2e8d"))
            hashCode = hashCode;
        if(dir == null){
            dirNew = new HashBag<>();
            dirNew.add(fileName);
            hash2Directory.put(hashCode,dirNew);
        }else{
            String fileNameHash = this.generateHashString(fileName);
            System.out.println("FileName " + fileName);
            for(String d : dir) {
                if (this.generateHashString(d).equals(fileNameHash)) {
                    isPresent = true;
                }
            }
            if(!isPresent) {
                // hash from fileName ,  then walk over dir and build hash codes and compare with fileNme , add only if not equal
                dir.add(fileName);
                hash2Directory.put(hashCode, dir);
            }
        }
    }
    public void logAll(){
        try {
            FileWriter fileWriter = new FileWriter("word-hash2Directory.txt");
            for(Map.Entry<String, Bag<String>> bagEntry: hash2Directory.entrySet()){
                String s = "HashCode from Word: " + bagEntry.getKey();
                log.info(s);
                fileWriter.write(s + "\n");
                for(String entry : bagEntry.getValue()){
                    String s1 = "File: " + entry;
                    fileWriter.write(s1 + "\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();
        }catch(
                IOException e){
            log.error(e.getMessage());
        }
    }
}
