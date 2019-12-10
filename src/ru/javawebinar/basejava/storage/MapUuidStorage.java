package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }

    @Override
    void doSave(Resume resume, Object uuid) {
        storage.put((String) uuid, resume);
    }

    @Override
    void doUpdate(Resume resume, Object uuid) {
        storage.replace((String) uuid, resume);
    }

    @Override
    Resume doGet(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    void doDelete(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>(storage.values());
        resumeList.sort(AbstractArrayStorage.RESUME_FULL_NAME_COMPARATOR);
        return resumeList;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
