package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firmName;
    private String httpLink;
    private List<Position> positionList;

    public Organisation(String firmName, String httpLink, List<Position> positionList) {
        this.firmName = Objects.requireNonNull(firmName, "The field firmName can't be null");
        this.httpLink = httpLink;
        this.positionList = positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return firmName.equals(that.firmName) &&
                httpLink.equals(that.httpLink) &&
                positionList.equals(that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, httpLink, positionList);
    }

}
