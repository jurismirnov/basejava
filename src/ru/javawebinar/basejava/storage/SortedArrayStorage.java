package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    void putInStorage(Resume resume, int idx) {
        int insertIndex = -(idx + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    void remove(int idx) {
        //shift all records after idx to the left
        if (size - idx + 1 >= 0) System.arraycopy(storage, idx + 1, storage, idx, size - idx - 1);
    }

    @Override
    Integer getSearchKey(String uuid) {
        Resume resume= new Resume(uuid, " ");
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_UUID_COMPARATOR);
    }

    public static class MapUuidStorage extends AbstractStorage {
        private Map<String, Resume> storage = new HashMap<>();

        @Override
        boolean isExist(Object uuid) {
            return storage.containsKey(uuid);
        }

        @Override
        void doSave(Resume resume, Object uuid) {
            storage.put((String) uuid, resume);
        }

        @Override
        void doUpdate(Resume resume, Object uuid) {
            storage.replace((String) uuid, resume);
        }

        @Override
        Resume doGet(Object uuid) {
            return storage.get(uuid);
        }

        @Override
        void doDelete(Object uuid) {
            storage.remove(uuid);
        }

        @Override
        String getSearchKey(String uuid) {
            return uuid;
        }

        @Override
        List<Resume> getAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public void clear() {
            storage.clear();
        }

        @Override
        public int size() {
            return storage.size();
        }
    }
}
