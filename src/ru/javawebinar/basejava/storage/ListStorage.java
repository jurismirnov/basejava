package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

class ListStorage extends AbstractStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    void doSave(Resume resume, Object keyToSave) {
        storage.add(resume);
    }

    @Override
    void doUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    void doDelete(Object key) {
        storage.remove((Integer) key);
    }

    @Override
    Integer getSearchKey(String uuid) {
        int i = 0;
        Resume resume = new Resume(uuid);
        for (Resume res : storage) {
            if (res.equals(resume)) {
                return i;
            }
            i++;
        }
        return null;
    }

    @Override
    boolean exists(Object getSearchKeyOutput) {
        return getSearchKeyOutput != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
