package ru.javawebinar.basejava.model;

import java.util.Objects;

public enum ContactType {
    PHONENR("Телефон"),
    SKYPE("Skype"),
    EMAIL("Почта"),
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
}
