package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * Checks the existence of resume in storage by uuid(String)
     *
     * @return the index of resume, -1 if not found
     */
    @Override
    protected Object checkExistence(Resume resume) {
        String pattern = resume.getUuid();
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(pattern)) {
                exists = true;
                return i;
            }
        }
        exists = false;
        return -1;
    }

    @Override
    void putInStorage(Resume resume, int idx) {
        storage[size] = resume;
    }

    @Override
    void remove(int idx) {
        storage[idx] = storage[size - 1];
    }

}
