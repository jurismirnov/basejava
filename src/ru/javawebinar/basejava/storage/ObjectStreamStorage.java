package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(resume);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(is)){
            return (Resume) ois.readObject();
        }catch(ClassNotFoundException e){
            throw new StorageException(null,"Error read resume", e);
        }
    }
}
