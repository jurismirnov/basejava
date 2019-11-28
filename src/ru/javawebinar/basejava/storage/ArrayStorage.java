package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    /**
     * update resume in the storage
     */
    public void update(Resume resume) {
        int idx = checkExistence(resume.getUuid());
        if (idx > -1) {
            storage[idx] = resume;
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
            storage[idx] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("DELETE: ERROR: Can't find the resume in storage!");
        }
    }


    /**
     * Checks the existence of resume in storage by uuid(String)
     *
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
