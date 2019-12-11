package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    void putInStorage(Resume resume, int idx) {
        int insertIndex = -(idx + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    void remove(int idx) {
        //shift all records after idx to the left
        if (size - idx + 1 >= 0) System.arraycopy(storage, idx + 1, storage, idx, size - idx - 1);
    }

    @Override
    Object getSearchKey(String uuid) {
        Resume resume= new Resume(uuid, " ");
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_UUID_COMPARATOR);
    }
}
