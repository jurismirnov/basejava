package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    void putInStorage(Resume resume, int idx) {
        int insertIndex = -(idx + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;

    }

    @Override
    void remove(int idx) {
        //shift all records after idx to the left
        if (size - idx + 1 >= 0) System.arraycopy(storage, idx + 1, storage, idx + 1 - 1, size - idx + 1);
    }

    @Override
    Object getSearchKey(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }


}
