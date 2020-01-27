package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends Section {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
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
        TextSection that = (TextSection) o;
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

    @Override
    public String asString() {
        return getText();
    }
}
