package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this(uuid, "Naprimerov Naprimer");
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Resume uuid can not be null.");
        Objects.requireNonNull(uuid, "Resume fullName can not be null.");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid + "   ||   " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        return (fullName + uuid).compareTo(o.fullName + o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return (fullName + uuid).equals(resume.fullName + resume.uuid);
    }

    @Override
    public int hashCode() {
        return (fullName + uuid).hashCode();
    }

}
