package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorageTest {
    private final Storage storage = new ListStorage();
    private final static String UUID1 = "uuid1";
    private final static String UUID2 = "uuid2";
    private final static String UUID3 = "uuid3";
    private final static String UUID4 = "uuid4";
    private final static String DUMMY = "dummy";
    private final static Resume R1 = new Resume(UUID1);
    private final static Resume R2 = new Resume(UUID2);
    private final static Resume R3 = new Resume(UUID3);
    private final static Resume R4 = new Resume(UUID4);


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
        Assert.assertEquals(R2, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(allResume.length, storage.size());
        ArrayList<Resume> resumeList = new ArrayList<>();
        resumeList.add(R1);
        resumeList.add(R2);
        resumeList.add(R3);
        resumeList.add(R4);
        for (Resume res : allResume) {
            if (!resumeList.contains(res)) {
                Assert.fail("The resume " + res + " not found!");
            }
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}
