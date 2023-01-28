package testTask;

import testTask.strategy.Strategy;
import testTask.util.FileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String INPUT_PATH = "input.txt";
    private static final String OUTPUT_PATH = "output.txt";

    public static void main(String[] args) {
        String line;
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(INPUT_PATH))) {
            Strategy strategy = new Strategy();
            while ((line = bufferedReader.readLine()) != null) {
                strategy.lineStrategy(line);
            }
            FileWriter.writeTo(OUTPUT_PATH, strategy.getResult());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
