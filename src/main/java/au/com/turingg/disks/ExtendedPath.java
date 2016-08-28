package au.com.turingg.disks;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A path with its metadata.
 *
 * @author Behrang Saeedzadeh
 */
public class ExtendedPath {

    private final String name;

    private final String absolutePath;

    private final String extension;

    private final String mimeType;

    private final Long size;

    private final FileType type;

    public ExtendedPath(String name, String absolutePath, String extension, String mimeType, FileType type, Long size) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.extension = extension;
        this.mimeType = mimeType;
        this.type = type;
        this.size = size;
    }

    public ExtendedPath(Path path, String mimeType) {
        final File file = path.toFile();

        this.name = file.getName();
        this.absolutePath = file.getAbsolutePath();
        this.extension = FilenameUtils.getExtension(absolutePath);
        this.mimeType = mimeType;

        if (Files.isDirectory(path)) {
            this.type = FileType.DIRECTORY;
        } else if (Files.isRegularFile(path)) {
            this.type = FileType.REGULAR_FILE;
        } else {
            this.type = FileType.OTHER;
        }

        this.size = file.length();
    }

    public ExtendedPath(Path path) {
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
