package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapResumeStorageTest extends AbstractArrayStorageTest{
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Test
    public void clear() {
        Assert.assertTrue("Array not empty, ok!", storage.size() > 0);
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Override
    @Test
    public void save() {
        Resume newResume = new Resume("uuid_save", "test");
        storage.save(newResume);
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(newResume, storage.get("uuid_save"));
    }

    @Override
    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID2, FULL_NAME2));
    }

    @Override
    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflow() {
    }

    @Override
    @Test
    public void update() {
        Resume resume = new Resume(UUID2, FULL_NAME2);
        Assert.assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID2));
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(DUMMY, FULL_NAME1));
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID2);
        Assert.assertEquals(size - 1, storage.size());
        storage.get(UUID2);
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Override
    @Test
    public void get() {
        Resume resume = storage.get(UUID2);
        Assert.assertEquals(R2, resume);
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Override
    @Test
    public void getAllSorted() {
        List allResume = storage.getAllSorted();
        ArrayList<Resume> resumeList = new ArrayList<>(Arrays.asList(R4, R3, R2, R1));
        Assert.assertEquals(resumeList.size(), allResume.size());
        Assert.assertEquals(resumeList, allResume);
    }

    @Override
    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

}
