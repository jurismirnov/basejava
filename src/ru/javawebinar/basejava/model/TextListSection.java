package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class TextListSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<String> records;

    public TextListSection() {
    }

    public TextListSection(List<String> records) {
        this.records = Objects.requireNonNull(records,"The Text list can't be null");
    }

    public List<String> getRecords() {
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextListSection that = (TextListSection) o;
        return records.equals(that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(records);
    }

    @Override
    public String toString() {
        return "TextListSection{" +
                "records=" + records +
                '}';
    }
}
