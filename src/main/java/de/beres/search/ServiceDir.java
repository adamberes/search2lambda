package de.beres.search;

import de.beres.search.operations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceDir {

    @Autowired
    FileVisitorImpl fileVisitor;
    @Autowired
    FileVisitorImplCopy fileVisitorImplCopy;

    public void work(Settings settings) {
        if(settings.getOperation().equals(Operation.COPY))
            fileVisitorImplCopy.callVisitor(settings);
        else if(settings.getOperation().equals(Operation.MOVE))
            fileVisitor.callVisitor(settings);
        else if(settings.getOperation().equals(Operation.INDEX))
            fileVisitor.callVisitor(settings);
    }
}
