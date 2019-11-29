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
    void putInStorage(int index, Resume resume, int idx) {
        if (getInsertIndex() < 0) {
            System.out.println("PutInStorage:Error the resume already exists!");
        } else {
            Resume[] resumeEnd = Arrays.copyOfRange(storage, getInsertIndex(), index);
            storage[getInsertIndex()] = resume;
            int k = 1;
            for (Resume res : resumeEnd) {
                storage[getInsertIndex() + k] = res;
                k++;
            }
            setInsertIndex(-1); //set the insert index default value
        }
    }

    /**
     * remove resume from array
     * array is sorted, no gaps
     * @param idx - position of resume in storage
     */
    @Override
    void remove(int idx) {
        for (int i = idx + 1; i < size; i++) {
            storage[i - 1] = storage[i]; //shift all records after idx to the left
        }
    }

    /**
     * Checks the existence of resume in storage by uuid(String) using binarySearch
     * @return the index of resume, -1 if not found
     */
    @Override
    protected int checkExistence(String uuid) {
        Resume resTemp = new Resume();
        resTemp.setUuid(uuid);
        Resume[] tempStorage = getAll();
        int idx = Arrays.binarySearch(tempStorage, resTemp);
        if (idx >= 0) {
            return idx;
        } else {
            System.out.println("Check existence: the resume with uuid " + uuid + " not found.");
            setInsertIndex(-(idx + 1)); //save the insert position
            return -1;
        }
    }

}