package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;

public class MainTestSortedArrayStorage {

    /**
     * Test for your ru.webinar.basejava.storage.SortedArrayStorage implementation
     */
    private static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid3");
        Resume r3 = new Resume();
        r3.setUuid("uuid2");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        //print all records already sorted
        printAll();


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());


        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //Testing update
        System.out.println("********* Start Testing update *********");
        System.out.println("Get r3: " + r3.getUuid());
        System.out.println("Updating r3");
        r3.setUuid("uuid3_updated");
        ARRAY_STORAGE.update(r3);
        System.out.println("Get r3: " + r3.getUuid());
        System.out.println("Print all:");
        printAll();
        System.out.println("********* End Testing update *********");
        System.out.println();

        //testing delete
        System.out.println("********* Start Testing delete *********");
        System.out.println("Deleting resume with uuid 'uuid3_updated'");
        ARRAY_STORAGE.delete("uuid3_updated");
        System.out.println("Print all:");
        printAll();
        System.out.println("Storage size: " + ARRAY_STORAGE.size());
        System.out.println("********* End Testing delete *********");
        System.out.println();

        System.out.println("********* Start Testing clear *********");
        ARRAY_STORAGE.clear();
        System.out.println("Print all:");
        printAll();
        System.out.println("Storage size: " + ARRAY_STORAGE.size());
        System.out.println("********* End Testing clear *********");
        System.out.println();

    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
