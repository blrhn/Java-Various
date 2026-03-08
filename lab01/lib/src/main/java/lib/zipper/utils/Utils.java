package lib.zipper.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class Utils {
    public static void transferStream(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int length;

        if (fileInputStream != null) {
            while ((length = fileInputStream.read(buffer)) >= 0) {
                zipOutputStream.write(buffer, 0, length);
            }
        }
    }
}
