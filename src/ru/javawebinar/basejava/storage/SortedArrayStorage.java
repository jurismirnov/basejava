package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    private int insertIndex = -1;

    private int getInsertIndex() {
        return insertIndex;
    }

    private void setInsertIndex(int insertIndex) {
        this.insertIndex = insertIndex;
    }

    /**
     * put resume in the storage (Pattern)
     * (the array is sorted after insert)
     */
    @Override
    void putInStorage(Resume resume, int idx) {
        if (insertIndex < 0) {
            System.out.println("PutInStorage:Error the resume already exists!");
        } else {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;

            setInsertIndex(-1); //set the insert index default value
        }
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
    protected int checkExistence(String uuid) {
        Resume resTemp = new Resume();
        resTemp.setUuid(uuid);
        int idx = Arrays.binarySearch(getAll(), resTemp);
        if (idx >= 0) {
            return idx;
        } else {
            System.out.println("Check existence: the resume with uuid " + uuid + " not found.");
            setInsertIndex(-(idx + 1)); //save the insert position
            return -1;
        }
    }

}