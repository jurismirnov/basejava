package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFileListRecursive {
    private static void fileListRecursive(File file) {
        File[] fileArray = Objects.requireNonNull(file.listFiles());
        for (File f : fileArray) {
            if (f.isDirectory()) {
                fileListRecursive(f);
            } else {
                System.out.println(f.getName());
            }
        }
    }

    public static void main(String[] args) {
        File startDirectory = new File("C:\\Users\\Juri\\basejava\\src");
        fileListRecursive(startDirectory);
    }
}
