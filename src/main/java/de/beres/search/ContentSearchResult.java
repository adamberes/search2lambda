package de.beres.search;

import de.beres.search.content.WordTransitiv2Directory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class ContentSearchResult {
    @Autowired
    WordTransitiv2Directory wordTransitiv2Directory;
}
