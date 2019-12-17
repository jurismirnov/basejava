package ru.javawebinar.basejava.model;

import java.util.List;

public class TextList extends Section {
    private List<String> records;

    public TextList(List<String> records) {
        this.records = records;
    }

    public List<String> getRecords() {
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
