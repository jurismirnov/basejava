package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected File directory;

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "diractory must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " directory is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    boolean isExist(File file) {
        return file.exists();
    }

    @Override
    void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can not create file", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getName(), "Can not delete the file" + file.getName());
        }
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    List<Resume> getAll() {
        ArrayList<Resume> allResume = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            allResume.add(doGet(file));
        }
        return allResume;
    }

    @Override
    public void clear() {
        checkDirectoryNotNull();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        checkDirectoryNotNull();
        return Objects.requireNonNull(directory.list()).length;
    }

    private void checkDirectoryNotNull() {
        if (directory.listFiles() == null || directory.list() == null) {
            throw new StorageException("File list is null  ", directory.getName());
        }
    }
}
