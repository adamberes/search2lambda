package de.beres.search.controller;

import java.util.List;

public class MessageBean {
    private String name;
    private String message;
    private List<DirectoryData> directoryData;

    public List<DirectoryData> getDirectoryListData() {
        return directoryData;
    }
    public void setDirectoryListData(List<DirectoryData> directoryData) {
        this.directoryData = directoryData;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}