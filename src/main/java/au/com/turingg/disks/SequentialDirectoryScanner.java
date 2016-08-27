package au.com.turingg.disks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Behrang Saeedzadeh
 */
public class SequentialDirectoryScanner implements DirectoryScanner {

    private final PathVisitor pathVisitor;

    public SequentialDirectoryScanner(PathVisitor pathVisitor) {
        this.pathVisitor = pathVisitor;
    }

    @Override
    public void start(final Path directory) throws IOException {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(
                    String.format(
                            "[%s] is not a directory",
                            directory.toAbsolutePath().toString()
                    )
            );
        }

        Files.walkFileTree(directory, pathVisitor);
    }
}
