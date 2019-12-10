package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapStorage;

import java.util.List;

public class MainTestMapStorage {
    private static final MapStorage MAP_STORAGE = new MapStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Sacha Sidorova");
        Resume r2 = new Resume("uuid3", "Maria Vasina");
        Resume r3 = new Resume("uuid2", "Boris Petrov");

        MAP_STORAGE.save(r1);
        MAP_STORAGE.save(r2);
        MAP_STORAGE.save(r3);

        //testing getAllSorted
        System.out.println("********* Start Testing getAllSorted *********");
        List<Resume> allResumeSorted = MAP_STORAGE.getAllSorted();
        for (Resume res : allResumeSorted) {
            System.out.println(res.getUuid() + "  ||  " + res.getFullName());
        }
        System.out.println("********* End Testing GetAllSorted *********");
    }
}

