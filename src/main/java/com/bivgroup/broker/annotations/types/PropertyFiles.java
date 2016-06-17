package com.bivgroup.broker.annotations.types;

/**
 * Created by bush on 12.05.2016.
 */

public enum PropertyFiles {
    EXT("viewmodel.properties"),
    IN("external.properties");

    private String fileName;

    PropertyFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

}

