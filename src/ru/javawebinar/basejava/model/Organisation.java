package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organisation {
    private String firmName;
    private String httpLink;
    private List<Epoch> epochList;

    public Organisation(String firmName, String httpLink, List<Epoch> epochList) {
        this.firmName = Objects.requireNonNull(firmName, "The field firmName can't be null");
        this.httpLink = httpLink;
        this.epochList = epochList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return firmName.equals(that.firmName) &&
                httpLink.equals(that.httpLink) &&
                epochList.equals(that.epochList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, httpLink, epochList);
    }

}
