package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LENGTH; i++) {
                storage.save(new Resume("Ivan Ivanov"));
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow during the filling");
        }
        storage.save(new Resume("Petr Petrov"));
    }
}
