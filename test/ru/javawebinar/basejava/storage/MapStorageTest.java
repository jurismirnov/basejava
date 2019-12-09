package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {
    }

    @Override
    @Test
    public void getAll() {
        ArrayList<Resume> expected = new ArrayList<>(Arrays.asList(R1, R2, R3, R4));
        ArrayList<Resume> allResume = new ArrayList<>(Arrays.asList(storage.getAll()));
        Collections.sort(allResume);
        Assert.assertEquals(expected.size(), allResume.size());
        Assert.assertEquals(expected, allResume);
    }
}
