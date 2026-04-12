package classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ProcessClassLoader extends ClassLoader {
    private static final String customExtension = ".custom";
    private final Path directory;

    public ProcessClassLoader(Path directory) {
        this.directory = directory.toAbsolutePath().normalize();
    }

    public List<Path> getLoadableClasses() {
        try (Stream<Path> paths = Files.walk(directory) ) {
            return paths.filter(p -> p.toString().endsWith(customExtension)).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    public Class<?> loadClass(Path path) throws ClassNotFoundException {
        String name = directory.relativize(path).toString()
                .replace(File.separatorChar, '.')
                .replace(customExtension, "");

        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        /*Class<?> alreadyLoaded = findLoadedClass(name);
        if (alreadyLoaded != null) {
            return alreadyLoaded;
        }*/

        Path path = directory.resolve(name.replace('.', File.separatorChar) + customExtension);
        try {
            byte[] classBytes = Files.readAllBytes(path);

            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
