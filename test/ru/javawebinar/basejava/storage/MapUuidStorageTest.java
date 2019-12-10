package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;

public class MapUuidStorageTest extends AbstractArrayStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {
    }

    @Override
    @Test
    public void getAllSorted() {
        ArrayList<Resume> expected = new ArrayList<>(Arrays.asList(R4, R3, R2, R1));
        ArrayList<Resume> allResume = new ArrayList<>(storage.getAllSorted());
        Assert.assertEquals(expected.size(), allResume.size());
        Assert.assertEquals(expected, allResume);
    }
}
