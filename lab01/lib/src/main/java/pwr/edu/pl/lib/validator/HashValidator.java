package pwr.edu.pl.lib.validator;

import pwr.edu.pl.lib.validator.model.CheckedFile;
import pwr.edu.pl.lib.validator.model.HashStatus;
import pwr.edu.pl.lib.zipper.Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HashValidator {
    private HashValidator() {}

    public static List<CheckedFile> validateFiles(List<File> files) {
        return files.stream()
                .map(HashValidator::validate)
                .toList();
    }

    private static CheckedFile validate(File file) {
        File hashFile = new File(file.getAbsolutePath() + ".sha256");

        if (!hashFile.exists()) {
            return new CheckedFile(file, "-", HashStatus.MISSING);
        }

        String calculatedHash = Encoder.sha256(file);
        String expectedHash = readHashFromFile(hashFile);
        HashStatus hashStatus = calculatedHash.equals(expectedHash) ? HashStatus.MATCH : HashStatus.MISMATCH;

        return new CheckedFile(file, expectedHash, hashStatus);
    }

    private static String readHashFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
