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
            setInsertIndex(-1);
        }
    }

    void remove(int idx) {
        for (int i = idx + 1; i < size; i++) {
            storage[i - 1] = storage[i]; //shift all records after idx to the left
        }
    }

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