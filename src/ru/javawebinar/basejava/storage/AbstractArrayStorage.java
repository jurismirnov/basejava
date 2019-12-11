package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LENGTH = 10_000;
    final Resume[] storage = new Resume[STORAGE_LENGTH];
    int size = 0; //shows the position of first null

    abstract void putInStorage(Resume resume, int idx);

    abstract void remove(int idx);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume resume, Object keyToSave) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            putInStorage(resume, (int) keyToSave);
            size++;
        }
    }

    @Override
    public void doUpdate(Resume resume, Object key) {
        storage[(Integer) key] = resume;
    }

    @Override
    public void doDelete(Object key) {
        remove((Integer) key);        //this step may differ for different storages
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    @Override
    boolean isExist(Object getSearchKeyOutput) {
        return ((Integer) getSearchKeyOutput >= 0);
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] resumeArray = Arrays.copyOf(storage, size);
        Arrays.sort(resumeArray);//, RESUME_FULL_NAME_COMPARATOR);
        return Arrays.asList(resumeArray);
    }


    public int size() {
        return size;
    }
}
