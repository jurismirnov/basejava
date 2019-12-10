package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;

public class MainTestSortedArrayStorage {

    /**
     * Test for your ru.webinar.basejava.storage.SortedArrayStorage implementation
     */
    private static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1","Sacha Sidorova");
        Resume r2 = new Resume("uuid3","Maria Vasina");
        Resume r3 = new Resume("uuid2","Boris Petrov");
        Resume r4 = new Resume("uuid11","Anton Ivanov");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        //print all records already sorted
        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //testing delete
        System.out.println("********* Start Testing delete *********");
        System.out.println("Deleting resume with uuid 'uuid1'");
        ARRAY_STORAGE.delete("uuid1");
        printAll();
        System.out.println("Storage size: " + ARRAY_STORAGE.size());
        System.out.println("********* End Testing delete *********");
        System.out.println();

        System.out.println("********* Start Testing clear *********");
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Storage size: " + ARRAY_STORAGE.size());
        System.out.println("********* End Testing clear *********");
        System.out.println();

    }

    private static void printAll() {
        System.out.println("\nPrint all:");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
