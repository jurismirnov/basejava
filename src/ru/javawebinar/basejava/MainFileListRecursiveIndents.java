package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFileListRecursiveIndents {
    private static void fileListRecursive(File file, String indent) {
        File[] fileArray = Objects.requireNonNull(file.listFiles());
        for (File f : fileArray) {
            if (f.isDirectory()) {
                System.out.println(indent + "Directory: " + f.getName());
                fileListRecursive(f, indent + "   "); //can set the shift length here
            } else {
                System.out.println(indent + f.getName());
            }
        }
    }

    public static void main(String[] args) {
        File startDirectory = new File("C:\\Users\\Juri\\basejava\\src");
        fileListRecursive(startDirectory,""); // here indent 0 is the start position of level 0
    }
}


