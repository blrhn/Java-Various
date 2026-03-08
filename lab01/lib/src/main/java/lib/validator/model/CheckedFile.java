package lib.validator.model;

import java.io.File;

public record CheckedFile(File file, String expectedHash, HashStatus hashStatus) {}
