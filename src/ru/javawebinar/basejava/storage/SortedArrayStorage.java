package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * put resume in the storage
     * (the array is sorted after insert)
     *
     * @param resume to put in storage; idx positive if already present in storage, negative if don't(shows insert position)
     */
    @Override
    void putInStorage(Resume resume, int idx) {
        int insertIndex = -(idx + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;

    }

    /**
     * remove resume from array
     * array is sorted, no gaps
     *
     * @param idx - position of resume in storage
     */
    @Override
    void remove(int idx) {
        //shift all records after idx to the left
        if (size - idx + 1 >= 0) System.arraycopy(storage, idx + 1, storage, idx + 1 - 1, size - idx + 1);
    }

    /**
     * Checks the existence of resume in storage by uuid(String) using binarySearch
     *
     * @return the index of resume, -1 if not found
     */
    @Override
    protected Object checkExistence(Resume resume) {
        int result = Arrays.binarySearch(storage, 0, size, resume);
        exists = (result >= 0);
        return result;
    }

}
