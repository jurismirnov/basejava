package ru.javawebinar.basejava.model;

import java.util.List;

public class ExPositionList extends Section {
    private List<ExPosition> exPositionList;

    public ExPositionList(List<ExPosition> exPositionList) {
        this.exPositionList = exPositionList;
    }

    public List<ExPosition> getExPositionList() {
        return exPositionList;
    }

    public void setExPositionList(List<ExPosition> exPositionList) {
        this.exPositionList = exPositionList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
