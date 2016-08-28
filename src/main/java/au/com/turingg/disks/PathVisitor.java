package au.com.turingg.disks;

import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Behrang Saeedzadeh
 */
public class PathVisitor implements FileVisitor<Path> {

    private final Tika tika;
    private final Consumer<ExtendedPath> consumer;
    private final BiConsumer<Path, IOException> errorHandler;

    public PathVisitor(final Consumer<ExtendedPath> consumer, final BiConsumer<Path, IOException> errorHandler) {
        this.tika = new Tika();
        this.consumer = consumer;
        this.errorHandler = errorHandler;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        final ExtendedPath ep = new ExtendedPath(dir);

        consumer.accept(ep);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        final String mimeType = detectMimeType(path).orElse(null);

        final ExtendedPath ep = new ExtendedPath(
                path,
                mimeType
        );

        consumer.accept(ep);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException ex) throws IOException {
        errorHandler.accept(file, ex);

        if (Files.isDirectory(file)) {
            return FileVisitResult.SKIP_SUBTREE;
        } else {
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException ex) throws IOException {
        if (ex != null) {
            errorHandler.accept(dir, ex);
        }

        return FileVisitResult.CONTINUE;
    }

    private Optional<String> detectMimeType(Path path) {
        if (!Files.isRegularFile(path)) {
            return Optional.of("inode/irregular-file");
        }

        if (Files.isDirectory(path)) {
            return Optional.of("inode/directory");
        }

        try {
            final String mimeType = tika.detect(path.toFile());
            return Optional.of(mimeType);
        } catch (IOException ioe) {
            errorHandler.accept(path, ioe);

            return Optional.empty();
        }
    }
}
