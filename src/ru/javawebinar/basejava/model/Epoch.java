package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Epoch {
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Epoch(LocalDate startDate, LocalDate endDate, String position, String description) {
        this.startDate = Objects.requireNonNull(startDate, "The field startDate can't be null");
        this.endDate = Objects.requireNonNull(endDate, "The field endDate can't be null");
        this.position = Objects.requireNonNull(position, "The field position can't be null");
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epoch epoch = (Epoch) o;
        return startDate.equals(epoch.startDate) &&
                endDate.equals(epoch.endDate) &&
                position.equals(epoch.position) &&
                Objects.equals(description, epoch.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, position, description);
    }

    @Override
    public String toString() {
        return "Period{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
