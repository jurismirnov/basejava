package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    boolean exists(Object searchKey) {
        return storage.containsKey(searchKey.toString());
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
        return storage.get(key.toString());
    }

    @Override
    void doDelete(Object key) {
        storage.remove(key.toString());
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
