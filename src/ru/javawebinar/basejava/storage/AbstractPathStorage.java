package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbstractPathStorage extends AbstractStorage<Path>{
    protected Path path;

    protected AbstractPathStorage(Path path) {
        Objects.requireNonNull(path, "diractory must be not null");
        if (!path.isDirectory()) {
            throw new IllegalArgumentException(path.getAbsolutePath() + "is not directory");
        }
        if (!path.canRead() || !path.canWrite()) {
            throw new IllegalArgumentException(path.getAbsolutePath() + " directory is not readable/writable");
        }
        this.path = path;
    }

    @Override
    boolean isExist(Path path) {
        return Paths.;
    }

    @Override
    void doSave(Resume resume, Path path) {
        try {
            path.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can not create file", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
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
        return new File(path, uuid);
    }

    @Override
    List<Resume> getAll() {
        ArrayList<Resume> allResume = new ArrayList<>();
        for (File file : checkDirectoryNotNull()) {
            allResume.add(doGet(file));
        }
        return allResume;
    }

    @Override
    public void clear() {
        for (File file : checkDirectoryNotNull()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return checkDirectoryNotNull().length;
    }

    private File[] checkDirectoryNotNull() {
        File[] directoryArray = path.listFiles();
        if (directoryArray == null) {
            throw new StorageException("File list is null  ", path.getName());
        }
        return directoryArray;
    }

}
