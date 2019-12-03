package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid3");
        Resume r3 = new Resume("uuid2");
        Resume r4 = new Resume("uuid11");

        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
        storage.save(r4);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(5, storage.size());
    }

    @Test
    public void update() {
        int size = storage.size();
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        Assert.assertEquals(size, storage.size());
    }

    @Test
    public void get() {
        Resume resume = storage.get("uuid2");
        Assert.assertEquals(resume.getUuid(), "uuid2");
    }

    @Test
    public void delete() {
        int size = storage.size();
        storage.delete("uuid2");
        Assert.assertEquals(size - 1, storage.size());
    }

    @Test
    public void getAll() {
        Assert.assertEquals(storage.getAll().length, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() {
        storage.save(new Resume("uuid1"));
    }

    @Test(expected = StorageException.class)
    public void getOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }
}