package au.com.turingg.disks;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Behrang Saeedzadeh
 */
public class ExtendedPathTest {

    @Test
    public void toStringShouldPrintDetails() {
        ExtendedPath extendedPath = new ExtendedPath(
                "fileName.json",
                "path/to/fileName,json",
                "json",
                "application/json",
                FileType.REGULAR_FILE,
                1000L
        );

        Assert.assertThat(
                extendedPath.toString(),
                equalTo("ExtendedPath[name=fileName.json,absolutePath=path/to/fileName,json,extension=json,mimeType=application/json,size=1000,type=REGULAR_FILE]")
        );
    }

}
