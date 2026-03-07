package pwr.edu.pl.lib;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    public void createZipArchive(List<File> files, String archiveName) {
        String safeArchiveName = ensureZipExtension(archiveName);

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(safeArchiveName);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        ) {
            for (File file : files) {
                zipFileOrDirectory(file, file.getName(), zipOutputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createZipArchiveSHA256(List<File> files, String archiveName) {
        String safeArchiveName = ensureZipExtension(archiveName);

        createZipArchive(files, safeArchiveName);
        String hash = Encoder.sha256(new File(safeArchiveName));
        saveHashCode(hash, safeArchiveName);
    }

    private void zipFileOrDirectory(File file, String pathName, ZipOutputStream zipOutputStream) throws IOException {
        if (file.isHidden()) {
            return;
        }

        if (file.isDirectory()) {
            String dirName = pathName.endsWith("/") ? pathName : pathName + "/";
            zipOutputStream.putNextEntry(new ZipEntry(dirName));
            zipOutputStream.closeEntry();

            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    zipFileOrDirectory(child, pathName + "/" + child.getName(), zipOutputStream);
                }
            }
        } else {
            zipOutputStream.putNextEntry(new ZipEntry(pathName));
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                transferStream(fileInputStream, zipOutputStream);
            }

            zipOutputStream.closeEntry();
        }
    }

    private void transferStream(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int length;

        if (fileInputStream != null) {
            while ((length = fileInputStream.read(buffer)) >= 0) {
                zipOutputStream.write(buffer, 0, length);
            }
        }
    }

    private void saveHashCode(String hash, String archiveName) {
        try (PrintWriter pw = new PrintWriter(archiveName + ".sha256")) {
            pw.write(hash);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String ensureZipExtension(String archiveName) {
        if (!archiveName.endsWith(".zip")) {
            return archiveName + ".zip";
        }

        return archiveName;
    }
}
