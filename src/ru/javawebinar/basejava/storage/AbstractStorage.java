package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    /**
     * the variable "exists" must in absract method "checkexistence" gesetzen werden
     */
    protected boolean exists;   //exists resume in storage or not

    abstract void saving(Resume resume, Object result);

    abstract void updating(Resume resume, Object result);

    abstract Resume getting(Resume resume, Object object);

    abstract void deleting(Resume resume, Object result);

    abstract Object checkExistence(Resume resume);

    public void save(Resume resume) {
        Object result = doesNotExist(resume);
        saving(resume, result);
    }

    public void update(Resume resume) {
        Object result = doesExist(resume);
        updating(resume, result);
    }

    public void delete(String uuid) {
        Resume resume = new Resume(uuid);
        Object result = doesExist(resume);
        deleting(resume, result);
    }

    public Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        Object result = doesExist(resume);
        return getting(resume, result);
    }

    private Object doesExist(Resume resume) {
        Object object = checkExistence(resume);
        if (!exists) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return object;
    }

    private Object doesNotExist(Resume resume) {
        Object object = checkExistence(resume);
        if (exists) {
            throw new ExistStorageException(resume.getUuid());
        }
        return object;
    }

}
