package de.beres.search.operations;

import de.beres.search.content.WordTransitiv2Directory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

@Slf4j
@AllArgsConstructor
@Component
public class ParseFile {

    WordTransitiv2Directory wordTransitiv2Directory;

    public String extractContentUsingParser(InputStream stream, String key)
            throws IOException, TikaException, SAXException {

        org.apache.tika.parser.Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);
        String date = metadata.get(key);
        log.debug(date);
        return handler.toString();
    }
    public String getKeyValueExtractContentUsingParser(String fileName, String key, Operation operation)
            throws IOException, TikaException, SAXException {
        String date="";
        InputStream stream = new FileInputStream(fileName);
        Parser parser = new AutoDetectParser();
        ContentHandler handler0 = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        StringWriter any = new StringWriter();
        BodyContentHandler handler2 = new BodyContentHandler(any);
        if(stream.available()>0)
            parser.parse(stream, handler2, metadata, context);

        if(Operation.INDEX.compareTo(operation)==0) {
            String[] words = handler2.toString().split("[\t\n ]+");
            for (String word : words)
                wordTransitiv2Directory.addWord(word, fileName);
        }
        if(Operation.INDEX.compareTo(operation)==0) {
            date = metadata.get(key);
            if(date==null){
                Path path = Paths.get(fileName);
                BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
                FileTime fileTime = fileAttributes.creationTime();
                date = fileTime.toString();
            }
            log.debug(date);
        }
        if(Operation.COPY.compareTo(operation)==0) {
            date = metadata.get(key);
            log.debug(date);
        }
        return date;
    }
    public static String getKeyValueUsingDetector(String fileName, String key)
            throws IOException {
        //not getting metadata
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(fileName));
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();
        MediaType mediaType = detector.detect(stream,metadata);
        String date = metadata.get(key);
        log.debug(date);
        return date;
    }

}
