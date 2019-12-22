package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    protected File directory;
    protected StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        this.streamSerializer = streamSerializer;
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
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
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
        File[] directoryArray = directory.listFiles();
        if (directoryArray == null) {
            throw new StorageException("File list is null  ", directory.getName());
        }
        return directoryArray;
    }
}
