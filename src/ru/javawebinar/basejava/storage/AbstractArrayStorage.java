package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

abstract class AbstractArrayStorage extends AbstractStorage {
    final private int STORAGE_LENGTH = 10_000;
    final Resume[] storage = new Resume[STORAGE_LENGTH];
    int size = 0; //shows the position of first null

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume resume, Object keyToSave) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            putInStorage(resume, (int) keyToSave);
            size += 1;
        }
    }

    abstract void putInStorage(Resume resume, int idx);

    @Override
    public void doUpdate(Resume resume, Object keyToUpdate) {
        storage[(int) keyToUpdate] = resume;
    }

    @Override
    public void doDelete(Object keyToDelete) {
        remove((int) keyToDelete);        //this step may differ for different storages
        storage[size - 1] = null;
        size -= 1;
    }

    abstract void remove(int idx);

    @Override
    public Resume doGet(Object keyToGet) {
        return storage[(int) keyToGet];
    }

    @Override
    boolean exists(Object getSearchKeyOutput) {
        return ((int) getSearchKeyOutput >= 0);
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    public int length() {
        return STORAGE_LENGTH;
    }

}
