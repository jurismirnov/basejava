package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage {
    final private int STORAGE_LENGTH = 10_000;
    final private Resume[] storage = new Resume[STORAGE_LENGTH];
    private int size = 0; //shows the position of first null

    /**
     * clear all values in storage (change to null)
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * put resume in the storage
     */
    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("ERROR: the storage is full!");
        } else {
            int idx = checkExistence(resume.getUuid());
            if (idx > -1) {
                System.out.println("SAVE: ERROR: The resume with uuid " + resume.getUuid() + " already exists!");
            } else {
                //************************************
                putInStorage(size, resume, idx);
                //************************************
                size++;
            }
        }
    }

    abstract void putInStorage(int index, Resume resume, int idx);

    /**
     * update resume in the storage
     */
    public void update(Resume resume) {
        int idx = checkExistence(resume.getUuid());
        if (idx > -1) {
            delete(resume.getUuid());
            save(resume);
        } else {
            System.out.println("UPDATE: ERROR: The resume with uuid " + resume.getUuid() + "does not exists!");
        }
    }

    /**
     * returns the resume by uuid
     */
    public Resume get(String uuid) {
        int idx = checkExistence(uuid);
        if (idx > -1) {
            return storage[idx];
        } else {
            System.out.println("GET: ERROR: Can't find the resume in storage!");
            return null;
        }
    }

    /**
     * deletes the resume by uuid
     */
    public void delete(String uuid) {
        int idx = checkExistence(uuid);
        if (idx > -1) {
            remove(idx);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("DELETE: ERROR: Can't find the resume in storage!");
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
}
