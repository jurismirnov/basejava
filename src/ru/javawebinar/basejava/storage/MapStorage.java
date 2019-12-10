package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }

    @Override
    void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void doUpdate(Resume resume, Object key) {
        storage.replace((String) key, resume);
    }

    @Override
    Resume doGet(Object key) {
        return storage.get((String) key);
    }

    @Override
    void doDelete(Object key) {
        storage.remove((String) key);
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
        List<Resume> resumeList = new ArrayList<Resume>(storage.values());
        resumeList.sort(AbstractArrayStorage.RESUME_FULL_NAME_COMPARATOR);
        return resumeList;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
