package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    boolean isExist(Object searchKey) {
        return storage.containsKey(String.valueOf(searchKey));
    }

    @Override
    void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void doUpdate(Resume resume, Object key) {
        storage.replace(String.valueOf(key), resume);
    }

    @Override
    Resume doGet(Object key) {
        return storage.get(String.valueOf(key));
    }

    @Override
    void doDelete(Object key) {
        storage.remove(String.valueOf(key));
    }

    @Override
    Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
