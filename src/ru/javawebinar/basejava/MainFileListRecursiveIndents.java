package ru.javawebinar.basejava;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

public class MainFileListRecursiveIndents {
    private static final int shiftLength = 4; // next level will be shifted right to this amount of spaces

    private static void fileListRecursive(File file, int indent) {
        File[] fileArray = Objects.requireNonNull(file.listFiles());
        String shift = String.join("", Collections.nCopies(indent," "));
        for (File f : fileArray) {
            if (f.isDirectory()) {
                System.out.println(shift + "Directory: " + f.getName());
                fileListRecursive(f, indent + shiftLength);
            } else {
                System.out.println(shift + f.getName());
            }
        }
    }

    public static void main(String[] args) {
        File startDirectory = new File("C:\\Users\\Juri\\basejava\\src");
        fileListRecursive(startDirectory, 0); // here indent 0 is the start position of level 0
    }
}


