package au.com.turingg.disks;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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

    public ExtendedPath(String name, String absolutePath, String extension, String mimeType, Long size) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.extension = extension;
        this.mimeType = mimeType;
        this.size = size;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
