package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * Checks the existence of resume in storage by uuid(String)
     * @return the index of resume, -1 if not found
     */
    @Override
    protected int checkExistence(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    void putInStorage(int index, Resume resume, int idx) {
        storage[index] = resume;
    }

    @Override
    void remove(int idx) {
        storage[idx] = storage[size - 1];
    }

}
