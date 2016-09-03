package au.com.turingg.disks;

import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Behrang Saeedzadeh
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Please provide a path");
            System.exit(1);
        }

        Set<String> mimeTypes = new TreeSet<>();

        DirectoryScanner directoryScanner = new SequentialDirectoryScanner(
                new StatelessPathVisitor(
                        new Tika(),
                        extendedPath -> {
                            if (extendedPath.getMimeType() != null)
                                mimeTypes.add(extendedPath.getMimeType());
                        },
                        (path, e) ->
                                System.err.println(String.format("Error: %s for %s", e, path))
                )

        );

        directoryScanner.start(Paths.get(args[0]));

        mimeTypes.forEach(System.out::println);
    }

}
