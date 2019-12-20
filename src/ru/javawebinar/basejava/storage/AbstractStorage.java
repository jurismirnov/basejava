package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Comparator<Resume> COMMON_RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;
    protected abstract Resume doRead(InputStream is) throws IOException;

    abstract boolean isExist(SK searchKey);

    abstract void doSave(Resume resume, SK key);

    abstract void doUpdate(Resume resume, SK key);

    abstract Resume doGet(SK key);

    abstract void doDelete(SK key);

    abstract SK getSearchKey(String uuid);

    abstract List<Resume> getAll();

    public void save(Resume resume) {
        SK result = doesNotExist(resume.getUuid());
        doSave(resume, result);
    }

    public void update(Resume resume) {
        SK result = doesExist(resume.getUuid());
        doUpdate(resume, result);
    }

    public void delete(String uuid) {
        SK result = doesExist(uuid);
        doDelete(result);
    }

    public Resume get(String uuid) {
        SK result = doesExist(uuid);
        return doGet(result);
    }

    private SK doesExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK doesNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(COMMON_RESUME_COMPARATOR);
        return resumes;
    }
}
