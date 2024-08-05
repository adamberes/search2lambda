package de.beres.search;

import de.beres.search.operations.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Settings {
    private Boolean js;
    private Boolean jsx;
    private Boolean pdf;
    private Boolean docx;
    private Boolean nef;
    private Boolean jpg;
    private Path srcDirectory;
    private Path destDirectory;
    private Operation operation;// 1 filter; 2 index; 3 mirror
    ContentSearchResult contentSearchResult;

}
