package com.urise.webapp.model;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    //Uuid getter
    public String getUuid() {
        return uuid;
    }

    //Uuid setter
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
