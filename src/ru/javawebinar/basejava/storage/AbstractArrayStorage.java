package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    final private int STORAGE_LENGTH = 10_000;
    final Resume[] storage = new Resume[STORAGE_LENGTH];
    int size = 0; //shows the position of first null

    /**
     * clear all values in storage (change to null)
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * put resume in the storage (Pattern)
     */
    @Override
    public void saving(Resume resume, Object object) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            putInStorage(resume, (int) object);
            size += 1;
        }
    }

    abstract void putInStorage(Resume resume, int idx);

    /**
     * update resume in the storage (Pattern)
     */
    @Override
    public void updating(Resume resume, Object object) {
        storage[(int) object] = resume;
    }

    /**
     * deletes the resume by uuid (Pattern)
     */
    @Override
    public void deleting(Resume resume, Object object) {
        remove((int) object);        //this step may differ for different storages
        storage[size - 1] = null;
        size -= 1;
    }

    abstract void remove(int idx);

    /**
     * returns the resume by uuid (Pattern)
     */
    @Override
    public Resume getting(Resume resume, Object object) {
        return storage[(int) object];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the number of saved resumes
     */
    public int size() {
        return size;
    }

    /**
     * @return length of storage
     */
    public int length() {
        return storage.length;
    }

}
