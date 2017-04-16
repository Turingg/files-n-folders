package au.com.turingg.disks;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Behrang Saeedzadeh
 */
public class FileDetailsTest {

    @Test
    public void toStringShouldPrintDetails() {
        FileDetails fileDetails = new FileDetails(
                "fileName.json",
                "path/to/fileName,json",
                "json",
                "application/json",
                FileType.REGULAR_FILE,
                1000L
        );

        Assert.assertThat(
                fileDetails.toString(),
                equalTo("FileDetails[name=fileName.json,absolutePath=path/to/fileName,json,extension=json,mimeType=application/json,size=1000,type=REGULAR_FILE]")
        );
    }

}
