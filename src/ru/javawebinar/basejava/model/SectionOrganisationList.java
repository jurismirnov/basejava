package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class SectionOrganisationList extends Section {
    private List<Organisation> organisationList;

    public SectionOrganisationList(List<Organisation> organisationList) {

        this.organisationList = Objects.requireNonNull(organisationList,"The organisationList can't be null");
    }

    public List<Organisation> getOrganisationList() {
        return organisationList;
    }

    public void setOrganisationList(List<Organisation> organisationList) {
        this.organisationList = organisationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionOrganisationList that = (SectionOrganisationList) o;
        return organisationList.equals(that.organisationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationList);
    }

    @Override
    public String toString() {
        return "SectionOrganisationList{" +
                "organisationList=" + organisationList +
                '}';
    }
}
