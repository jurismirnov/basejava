package ru.javawebinar.basejava.model;

import java.util.Objects;

public enum ContactType {
    PHONENR("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = Objects.requireNonNull(title, "The field can't be null");
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
