package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    boolean isExist(Object uuid) {
        return storage.containsKey(uuid);
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
        return storage.get(uuid);
    }

    @Override
    void doDelete(Object uuid) {
        storage.remove(uuid);
    }

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
