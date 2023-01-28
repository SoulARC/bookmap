package testTask.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {
    public static void writeTo(String path, String result) {
        try {
            Files.write(Path.of(path), result.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file by path: " + path, e);
        }
    }
}

