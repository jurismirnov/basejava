package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class SectionTextList extends Section {
    private List<String> records;

    public SectionTextList(List<String> records) {
        this.records = Objects.requireNonNull(records,"The Text list can't be null");
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
