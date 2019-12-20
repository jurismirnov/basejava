package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Position(LocalDate startDate, LocalDate endDate, String position, String description) {
        this.startDate = Objects.requireNonNull(startDate, "The field startDate can't be null");
        this.endDate = Objects.requireNonNull(endDate, "The field endDate can't be null");
        this.position = Objects.requireNonNull(position, "The field position can't be null");
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return startDate.equals(position.startDate) &&
                endDate.equals(position.endDate) &&
                this.position.equals(position.position) &&
                Objects.equals(description, position.description);
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
