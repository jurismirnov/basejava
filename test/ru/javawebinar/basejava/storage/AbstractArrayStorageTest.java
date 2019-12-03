package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private final Resume r1 = new Resume("uuid1");
    private final Resume r2 = new Resume("uuid11");
    private final Resume r3 = new Resume("uuid2");
    private final Resume r4 = new Resume("uuid3");

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
        storage.save(r4);
    }

    @Test
    public void clear() {
        Assert.assertEquals(4, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid_save"));
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals("uuid_save", storage.get("uuid_save").toString());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid2"));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < storage.length(); i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow during the filling");
        }
        storage.save(new Resume());
    }


    @Test
    public void update() {
        int size = storage.size();
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        Assert.assertEquals(size, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete("uuid2");
        Assert.assertEquals(size - 1, storage.size());
        storage.get("uuid2");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        Resume resume = storage.get("uuid2");
        Assert.assertEquals("uuid2", resume.getUuid());
        Assert.assertEquals(r3, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Assert.assertEquals(storage.getAll().length, storage.size());
        Resume[] resumeArray = new Resume[]{r1, r2, r3, r4};
        Assert.assertArrayEquals(resumeArray, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}