package ru.javawebinar.basejava.model;

import java.util.Objects;

public class SectionText extends Section {
    private String text;

    public SectionText(String text) {
        this.text = Objects.requireNonNull(text, "Text field can't be null");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionText that = (SectionText) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public String toString() {
        return "simpleText{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
