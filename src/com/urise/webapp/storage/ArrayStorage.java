package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int index = 0; //shows the position of first null

    /**
     * clear all values in storage (change to null)
     */
    public void clear() {
        Arrays.fill(storage, 0, index, null);
        index = 0;
    }

    /**
     * put resume in the storage
     */
    public void save(Resume r) {
        if (index > 9998) {
            System.out.println("ERROR: the storage is full!");
        } else {
            int ind = checkExistence(r.getUuid());
            if (ind > -1) {
                System.out.println("SAVE: ERROR: The resume with uuid " + r.getUuid() + " already exists!");
            } else {
                storage[index] = r;
                index++;
            }
        }
    }

    /**
     * update resume in the storage
     */
    public void update(Resume r) {
        int ind = checkExistence(r.getUuid());
        if (ind > -1) {
            storage[ind] = r;
        } else {
            System.out.println("UPDATE: ERROR: The resume with uuid " + r.getUuid() + "does not exists!");
        }
    }

    /**
     * returns the resume by uuid
     */
    public Resume get(String uuid) {
        int ind = checkExistence(uuid);
        if (ind > -1) {
            return storage[ind];
        } else {
            System.out.println("GET: ERROR: Can't find the resume in storage!");
            return null;
        }
    }

    /**
     * deletes the resume by uuid
     */
    public void delete(String uuid) {
        int ind = checkExistence(uuid);
        if (ind > -1) {
            storage[ind] = storage[index - 1];
            storage[index - 1] = null;
            index--;
        } else {
            System.out.println("DELETE: ERROR: Can't find the resume in storage!");
        }
    }

    /**
     * Checks the existence of resume in storage by uuid(String)
     *
     * @return the index of resume, -1 if not found
     */
    private int checkExistence(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    /**
     * @return the number of saved resumes
     */
    public int size() {
        return index;
    }
}
