package au.com.turingg.disks;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 *
 * @author Behrang Saeedzadeh
 */
public interface DirectoryScanner {

    void start(Path directory) throws IOException;

    default void stop() throws IOException {
    }

}
