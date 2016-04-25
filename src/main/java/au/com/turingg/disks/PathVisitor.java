package au.com.turingg.disks;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import java.io.File;
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
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        final File file = path.toFile();

        final String fileName = file.getName();
        final String absolutePath = file.getAbsolutePath();
        final String extension = FilenameUtils.getExtension(absolutePath);
        final String mimeType = detectMimeType(path).orElse("");

        final long size = file.length();

        final ExtendedPath ep = new ExtendedPath(
                fileName,
                absolutePath,
                extension,
                mimeType,
                size
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
            return Optional.empty();
        }

        try {
            final String mimeType = tika.detect(path.toFile());
            return Optional.of(mimeType);
        } catch (IOException ioe) {
            return Optional.empty();
        }
    }
}
