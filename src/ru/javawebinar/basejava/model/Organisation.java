package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organisation {
    private String firmName;
    private String httpLink;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Organisation(String firmName, String httpLink, LocalDate startDate, LocalDate endDate, String position, String description) {
        this.firmName = Objects.requireNonNull(firmName,"The field firmName can't be null");
        this.httpLink = httpLink;
        this.startDate = Objects.requireNonNull(startDate,"The field startDate can't be null");
        this.endDate = Objects.requireNonNull(endDate,"The field endDate can't be null");
        this.position = Objects.requireNonNull(position,"The field position can't be null");
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExPosition{" +
                "firmName='" + firmName + '\'' +
                ", httpLink='" + httpLink + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(firmName, that.firmName) &&
                Objects.equals(httpLink, that.httpLink) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(position, that.position) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, httpLink, startDate, endDate, position, description);
    }
}
