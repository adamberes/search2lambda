package de.beres.search.controller;

import de.beres.search.content.WordTransitiv2Directory;
import org.apache.commons.collections4.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
public class SocketController {

    int i =0;
    @Autowired
    WordTransitiv2Directory wordTransitiv2Directory;
    List<DirectoryData> stringList = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final static AtomicInteger index = new AtomicInteger(0); // Initialize

    @MessageMapping("/user-all")
    @SendTo("/topic/user")
    public MessageBean send(@Payload MessageBean message) {
        final AtomicInteger i = new AtomicInteger(0); // for creating the IDs
        System.out.println("SocketController: " + message.getMessage());
        System.out.println("index: " + index);
        List<DirectoryData> directoryListData;
        Bag<String> dirList=  wordTransitiv2Directory.getDirList4Word(message.getMessage());
        if(dirList == null) {
            directoryListData = List.of(createDirectoryData("Not Found"));
            message.setMessage("Not found");
        }else {
            directoryListData = dirList.stream()
                    .map(this::createDirectoryData).collect(Collectors.toList());
        }
        message.setDirectoryListData(directoryListData);
        return message;
    }
    private DirectoryData createDirectoryData(String directory) {
        DirectoryData directoryData = new DirectoryData();
        directoryData.setDirectory(directory);
        directoryData.setId(index.getAndIncrement());
        return directoryData;
    }
    @Autowired
    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void sendMessageToUser(String username, MessageBean message) {
        messagingTemplate.convertAndSendToUser(
                username, "/queue/reply", "message oh nice"
        );
    }
}