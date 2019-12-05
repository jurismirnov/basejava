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
    void doUpdate(Resume resume, Object keyToUpdate) {
        storage.set((int) keyToUpdate, resume);
    }

    @Override
    Resume doGet(Object keyToGet) {
        return storage.get((int) keyToGet);
    }

    @Override
    void doDelete(Object keyToDelete) {
        storage.remove((int) keyToDelete);
    }

    @Override
    Object getSearchKey(Resume resume) {
        int i=0;
        for (Resume res:storage){
            if (res.equals(resume)) {
                return i;
            }
            i+=1;
        }
        return null;
    }

    @Override
    boolean exists(Object getSearchKeyOutput) {
        return (getSearchKeyOutput != null);
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

    @Override
    public int length() {
        return storage.size();
    }

}
