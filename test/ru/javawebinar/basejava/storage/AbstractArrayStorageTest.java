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
    private final static String uuid1 = "uuid1";
    private final static String uuid2 = "uuid2";
    private final static String uuid3 = "uuid3";
    private final static String uuid4 = "uuid4";
    private final static Resume r1 = new Resume("uuid1");
    private final static Resume r2 = new Resume("uuid2");
    private final static Resume r3 = new Resume("uuid3");
    private final static Resume r4 = new Resume("uuid4");

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
        Assert.assertTrue("Array not empty, ok!", storage.size() > 0);
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume newResume = new Resume("uuid_save");
        storage.save(newResume);
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(newResume, storage.get("uuid_save"));
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
        Assert.assertNotSame(resume, storage.get("uuid2"));
        storage.update(resume);
        Assert.assertSame(resume, storage.get("uuid2"));
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
        Assert.assertEquals(r3, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(allResume.length, storage.size());
        Resume[] resumeArray = new Resume[]{r1, r2, r3, r4};
        Assert.assertArrayEquals(resumeArray, allResume);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}