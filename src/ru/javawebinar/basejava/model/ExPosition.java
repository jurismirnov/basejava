package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class ExPosition {
    private String firmName;
    private String httpLink;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public ExPosition(String firmName, String httpLink, LocalDate startDate, LocalDate endDate, String position, String description) {
        this.firmName = firmName;
        this.httpLink = httpLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
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
        ExPosition that = (ExPosition) o;
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
