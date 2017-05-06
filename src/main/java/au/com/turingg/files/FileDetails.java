package au.com.turingg.files;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * A file with its details that can be stored in a data store for querying, etc.
 *
 * @author Behrang Saeedzadeh
 */
public class FileDetails {

    private final String name;

    private final String absolutePath;

    private final String extension;

    private final String mimeType;

    private final Long size;

    private final FileType type;

    public FileDetails(String name, String absolutePath, String extension, String mimeType, FileType type, Long size) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.extension = extension;
        this.mimeType = mimeType;
        this.type = type;
        this.size = size;
    }

    public FileDetails(Path path, String mimeType) {
        final File file = path.toFile();

        this.name = file.getName();
        this.absolutePath = file.getAbsolutePath();
        this.extension = FilenameUtils.getExtension(absolutePath);
        this.mimeType = mimeType;
        this.size = file.length();

        if (Files.isDirectory(path)) {
            this.type = FileType.DIRECTORY;
        } else if (Files.isRegularFile(path)) {
            this.type = FileType.REGULAR_FILE;
        } else if (Files.isSymbolicLink(path)) {
            this.type = FileType.SYMLINK;
        } else {
            this.type = FileType.OTHER;
        }

    }

    public FileDetails(Path path) {
        this(path, null);
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public FileType getType() {
        return type;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
