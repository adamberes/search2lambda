package de.beres.search.operations;

import de.beres.search.ConfigurationProps;
import de.beres.search.Settings;
import lombok.Data;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class FileHandle {
    @Autowired
    ParseFile parseFile;
    @Autowired
    ConfigurationProps configurationProps;
    @Autowired
    DateOperations dateOperations;

    Settings settings;



    public void handleFile(Path file, Settings settings){
        this.settings = settings;
        String date="";
        int index = file.toFile().getName().toString().indexOf(".");
        String extension = file.toFile().getName().toString();
        extension = extension.substring(index +1).toLowerCase();
        try {
            switch(extension){
                case "jpg":
                    if(settings.getJpg().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(), configurationProps.getJpg(), settings.getOperation());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                case "pdf":
                    if(settings.getPdf().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(),configurationProps.getPdf(), settings.getOperation());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                case "nef":
                    if(settings.getNef().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(), configurationProps.getNef(), settings.getOperation());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                case "docx"://2022-10-17T05:49:00Z
                    if(settings.getDocx().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(), configurationProps.getDocx(), settings.getOperation());
                        //                       date = parseFile.getKeyValueUsingDetector(file.toString(), configurationProps.getJpg());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                case "js":
                    if(settings.getJs().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(), configurationProps.getJs(), settings.getOperation());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                case "jsx":
                    if(settings.getJsx().booleanValue()) {
                        date = parseFile.getKeyValueExtractContentUsingParser(file.toString(), configurationProps.getJsx(), settings.getOperation());
                        dateOperations.convertFromMetadata(date);
                    }
                    break;
                default:;
            }
            log.info("Datum: " + date);
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
