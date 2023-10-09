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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);

        if(Operation.INDEX.compareTo(operation)==0) {
            String[] words = handler.toString().split("[\t\n ]+");
            for (String word : words)
                wordTransitiv2Directory.addWord(word, fileName);
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
