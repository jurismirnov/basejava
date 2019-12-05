package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {


    @Override
    Object getSearchKey(Resume resume) {
        String pattern = resume.getUuid();
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(pattern)) {
                return i;
            }
        }
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
