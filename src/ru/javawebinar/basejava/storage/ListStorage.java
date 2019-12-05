package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    public ArrayList<Resume> storage;

    @Override
    void saving(Resume resume, Object result) {
        storage.add(resume);
    }

    @Override
    void updating(Resume resume, Object result) {
        storage.remove(resume);
        saving(resume, result);
    }

    @Override
    Resume getting(Resume resume, Object object) {
        int idx = (int) checkExistence(resume);
        if (exists) {
            return storage.get(idx);
        }
        return null;
    }

    @Override
    void deleting(Resume resume, Object result) {
        storage.remove(resume);
    }

    @Override
    Object checkExistence(Resume resume) {
        if (storage.contains(resume)) {
            exists = true;
            return storage.indexOf(resume);
        }
        exists = false;
        return null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size()];
        allResume = storage.toArray(allResume);
        return allResume;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public int length() {
        return storage.size();
    }


}
