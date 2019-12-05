package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

abstract class AbstractStorage implements Storage {

     abstract boolean exists(Object getSearchKeyOutput);

    abstract void doSave(Resume resume, Object keyToSave);

    abstract void doUpdate(Resume resume, Object keyToUpdate);

    abstract Resume doGet(Object keyToGet);

    abstract void doDelete(Object keyToDelete);

    abstract Object getSearchKey(Resume resume);

    public void save(Resume resume) {
        Object result = doesNotExist(resume);
        doSave(resume, result);
    }

    public void update(Resume resume) {
        Object result = doesExist(resume);
        doUpdate(resume, result);
    }

    public void delete(String uuid) {
        Object result = doesExist(new Resume(uuid));
        doDelete(result);
    }

    public Resume get(String uuid) {
        Object result = doesExist(new Resume(uuid));
        return doGet(result);
    }

    private Object doesExist(Resume resume) {
        Object searchKey = getSearchKey(resume);
        if (!exists(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private Object doesNotExist(Resume resume) {
        Object searchKey = getSearchKey(resume);
        if (exists(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

}
