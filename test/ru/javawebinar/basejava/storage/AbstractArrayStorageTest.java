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
    private final static String UUID1 = "uuid1";
    private final static String UUID2 = "uuid2";
    private final static String UUID3 = "uuid3";
    private final static String UUID4 = "uuid4";
    private final static String DUMMY = "dummy";
    private final static Resume R1 = new Resume(UUID1);
    private final static Resume R2 = new Resume(UUID2);
    private final static Resume R3 = new Resume(UUID3);
    private final static Resume R4 = new Resume(UUID4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
        storage.save(R4);
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
        storage.save(new Resume(UUID2));
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
        Resume resume = new Resume(UUID2);
        Assert.assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(DUMMY));
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID2);
        Assert.assertEquals(size - 1, storage.size());
        storage.get(UUID2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID2);
        Assert.assertEquals(R3, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(allResume.length, storage.size());
        Resume[] resumeArray = new Resume[]{R1, R2, R3, R4};
        Assert.assertArrayEquals(resumeArray, allResume);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}