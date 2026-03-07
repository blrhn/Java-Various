package pwr.edu.pl.lib.zipper;

import pwr.edu.pl.lib.zipper.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    public File createZipArchive(List<File> files, File saveDirectory, String archiveName) {
        String safeArchiveName = ensureZipExtension(archiveName);
        File finalArchive = getFinalPath(saveDirectory, safeArchiveName);

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(finalArchive);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        ) {
            for (File file : files) {
                zipFileOrDirectory(file, file.getName(), zipOutputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return finalArchive;
    }

    public void createZipArchiveSHA256(List<File> files, File saveDirectory, String archiveName) {
        File archive = createZipArchive(files, saveDirectory, archiveName);
        String hash = Encoder.sha256(archive);
        saveHashCode(hash, archive.getAbsolutePath());
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
                Utils.transferStream(fileInputStream, zipOutputStream);
            }

            zipOutputStream.closeEntry();
        }
    }


    public void saveHashCode(String hash, String name) {
        try (PrintWriter pw = new PrintWriter(name + ".sha256")) {
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

    private File getFinalPath(File saveDirectory, String archiveName) {
        return new File(saveDirectory, archiveName);
    }
}
