package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
    public void save(Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            int idx = checkExistence(resume.getUuid()); //this step may differ for different storages
            if (idx > -1) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                //************************************
                putInStorage(resume, idx); //this step may differ for different storages
                //************************************
                size++;
            }
        }
    }

    abstract void putInStorage(Resume resume, int idx);

    /**
     * update resume in the storage (Pattern)
     */
    public void update(Resume resume) {
        int idx = checkExistence(resume.getUuid()); //this step may differ for different storages
        if (idx > -1) {
            storage[idx] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    /**
     * returns the resume by uuid (Pattern)
     */
    public Resume get(String uuid) {
        int idx = checkExistence(uuid); //this step may differ for different storages
        if (idx > -1) {
            return storage[idx];
        }
        throw new NotExistStorageException(uuid);
    }

    /**
     * deletes the resume by uuid (Pattern)
     */
    public void delete(String uuid) {
        int idx = checkExistence(uuid); //this step may differ for different storages
        if (idx > -1) {
            remove(idx);        //this step may differ for different storages
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    abstract void remove(int idx);

    /**
     * Checks the existence of resume in storage by uuid(String)
     *
     * @return the index of resume, -1 if not found
     */
    abstract int checkExistence(String uuid);

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
