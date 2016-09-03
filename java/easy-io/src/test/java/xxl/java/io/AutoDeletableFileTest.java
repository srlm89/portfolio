package xxl.java.io;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;

import org.junit.Test;

public class AutoDeletableFileTest {

    @Test
    public void testFileExistsAndIsDeleted() {
        String tmpPath = "/tmp/L3JocklqYh_file.txt";
        File tmpFile = new File(tmpPath);
        assertFalse(tmpFile.exists());
        try (AutoDeletableFile file = new AutoDeletableFile(tmpPath)) {
            File existent = file.asExistent();
            assertTrue(existent.exists());
            assertTrue(existent.isFile());
            assertEquals(tmpPath, existent.getAbsolutePath());
        }
        assertFalse(tmpFile.exists());
    }
    
    @Test
    public void testFileNotCreatedIfMissingParent() {
    	String tmpPath = "/tmp/L3JocklqYh_jdhKe_2fH5/file.txt";
        File tmpFile = new File(tmpPath);
        assertFalse(tmpFile.getParentFile().exists());
        assertFalse(tmpFile.exists());
        try (AutoDeletableFile file = new AutoDeletableFile(tmpPath)) {
            try {
            	file.asExistent();
            	fail("Failure expected");
            }
            catch (Exception e) {
            	assertEquals("Unable to create file " + tmpPath, e.getMessage());
            	assertFalse(tmpFile.getParentFile().exists());
                assertFalse(tmpFile.exists());
            }
        }
    }
}
