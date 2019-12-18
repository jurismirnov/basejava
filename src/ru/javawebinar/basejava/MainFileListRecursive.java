package ru.javawebinar.basejava;

import java.io.File;

public class MainFileListRecursive {
    private static void fileListRecursive(File file) {
        File[] fileArray = file.listFiles();
        for (File f : fileArray) {
            if (f.isDirectory()) {
                System.out.println(f.getName() + " directory");
                fileListRecursive(f);
            } else {
                System.out.println(f.getName() + " file");
            }
        }
    }

    public static void main(String[] args) {
        File startDirectory = new File("C:\\Users\\Juri\\basejava\\src");
        fileListRecursive(startDirectory);
    }
}
