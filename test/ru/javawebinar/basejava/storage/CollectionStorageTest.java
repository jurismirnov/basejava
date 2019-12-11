package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class CollectionStorageTest extends AbstractArrayStorageTest {

    protected CollectionStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {
    }
}
