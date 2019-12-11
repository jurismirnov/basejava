package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> COMMON_RESUME_COMPARATOR = Comparator.comparing(o -> (o.getFullName() + o.getUuid()));

    abstract boolean isExist(Object searchKey);

    abstract void doSave(Resume resume, Object key);

    abstract void doUpdate(Resume resume, Object key);

    abstract Resume doGet(Object key);

    abstract void doDelete(Object key);

    abstract Object getSearchKey(String uuid);

    public void save(Resume resume) {
        Object result = doesNotExist(resume.getUuid());
        doSave(resume, result);
    }

    public void update(Resume resume) {
        Object result = doesExist(resume.getUuid());
        doUpdate(resume, result);
    }

    public void delete(String uuid) {
        Object result = doesExist(uuid);
        doDelete(result);
    }

    public Resume get(String uuid) {
        Object result = doesExist(uuid);
        return doGet(result);
    }

    private Object doesExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object doesNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> doSort(List<Resume> allResume) {
        allResume.sort(AbstractArrayStorage.COMMON_RESUME_COMPARATOR);
        return allResume;
    }
}
