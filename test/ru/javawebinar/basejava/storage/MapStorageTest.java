package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class MapStorageTest extends AbstractArrayStorageTest{

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override @Ignore @Test
    public void saveOverflow() {
    }

    @Override @Test
    public void getAll(){
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
}
