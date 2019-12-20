package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\Juri\\basejava\\storage");
    protected final Storage storage;
    protected final static String UUID1 = "uuid1";
    protected final static String UUID2 = "uuid2";
    protected final static String UUID3 = "uuid3";
    protected final static String UUID4 = "uuid4";
    protected final static String DUMMY = "dummy";
    protected final static String FULL_NAME1 = "Sacha Sidorova";
    protected final static String FULL_NAME2 = "Maria Vasina";
    protected final static String FULL_NAME3 = "Boris Petrov";
    protected final static String FULL_NAME4 = "Anton Ivanov";
    protected final static Resume R1 = new Resume(UUID1, FULL_NAME1);
    protected final static Resume R2 = new Resume(UUID2, FULL_NAME2);
    protected final static Resume R3 = new Resume(UUID3, FULL_NAME3);
    protected final static Resume R4 = new Resume(UUID4, FULL_NAME4);

    protected AbstractStorageTest(Storage storage) {
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
        Resume newResume = new Resume("uuid_save", "test");
        storage.save(newResume);
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(newResume, storage.get("uuid_save"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID2, FULL_NAME2));
    }


    @Test
    public void update() {
        Resume resume = new Resume(UUID2, FULL_NAME2);
        Assert.assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(DUMMY, FULL_NAME1));
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
        Assert.assertEquals(R2, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAllSorted() {
        List<Resume> allResume = storage.getAllSorted();
        ArrayList<Resume> resumeList = new ArrayList<>(Arrays.asList(R4, R3, R2, R1));
        Assert.assertEquals(resumeList.size(), allResume.size());
        Assert.assertEquals(resumeList, allResume);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}