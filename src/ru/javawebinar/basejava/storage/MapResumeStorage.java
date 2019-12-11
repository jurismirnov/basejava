package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void doUpdate(Resume resume, Object key) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    void doDelete(Object resume) {
        Resume res = (Resume) resume;
        storage.remove(res.getUuid());
    }

    @Override
    Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return doSort(new ArrayList<>(storage.values()));
    }

    @Override
    public int size() {
        return storage.size();
    }
}
