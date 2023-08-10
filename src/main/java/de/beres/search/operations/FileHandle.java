package de.beres.search.operations;

import de.beres.search.ConfigurationProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileHandle {
    @Autowired
    ParseFile parseFile;
    @Autowired
    ConfigurationProps configurationProps;
    public void handleFile(Path file){
        int index = file.toFile().getName().toString().indexOf(".");
        String extension = file.toFile().getName().toString();
        extension = extension.substring(index +1).toLowerCase();
        try {
            switch(extension){
                case "jpg":
                    parseFile.getKeyValueUsingDetector(new FileInputStream(file.toString()), configurationProps.getJpg());
                    break;
                case "pdf":
                    parseFile.extractContentUsingParser(new FileInputStream(file.toString()),configurationProps.getPdf());
                    break;
                case "nef":
                    extractContentUsingParser(new FileInputStream(file.toString()), configurationProps.getNef());
                    break;
                case "docx":
                    extractContentUsingParser(new FileInputStream(file.toString()), configurationProps.getDocx());
                    break;
                default:;
            }
        } catch (IOException e) {
                throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
    public static String extractContentUsingParser(InputStream stream, String key)
            throws IOException, TikaException, SAXException {

        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);
        String date = metadata.get(key);
        log.debug(date);
        return handler.toString();
    }
}
