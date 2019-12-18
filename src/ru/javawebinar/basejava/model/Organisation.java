package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Organisation {
    private String firmName;
    private String httpLink;
    private List<Period> periodList;

    private static class Period {
        private LocalDate startDate;
        private LocalDate endDate;
        private String position;
        private String description;

        private Period(LocalDate startDate, LocalDate endDate, String position, String description) {
            this.startDate = Objects.requireNonNull(startDate, "The field startDate can't be null");
            this.endDate = Objects.requireNonNull(endDate, "The field endDate can't be null");
            this.position = Objects.requireNonNull(position, "The field position can't be null");
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return startDate.equals(period.startDate) &&
                    endDate.equals(period.endDate) &&
                    position.equals(period.position) &&
                    Objects.equals(description, period.description);
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

    public Organisation(String firmName, String httpLink, List<Period> periodList) {
        this.firmName = Objects.requireNonNull(firmName, "The field firmName can't be null");
        this.httpLink = httpLink;
        this.periodList = periodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return firmName.equals(that.firmName) &&
                httpLink.equals(that.httpLink) &&
                periodList.equals(that.periodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, httpLink, periodList);
    }
}
