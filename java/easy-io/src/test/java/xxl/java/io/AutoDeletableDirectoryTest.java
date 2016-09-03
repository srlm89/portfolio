package xxl.java.io;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;

import org.junit.Test;

public class AutoDeletableDirectoryTest {

    @Test
    public void testDirectoryExistsAndIsDeleted() {
        String tmpPath = "/tmp/L3JocklqYh_BWIX-cgcHM";
        File tmpDir = new File(tmpPath);
        assertFalse(tmpDir.exists());
        try (AutoDeletableDirectory directory = new AutoDeletableDirectory(tmpPath)) {
            File existent = directory.asExistent();
            assertTrue(existent.exists());
            assertTrue(existent.isDirectory());
            assertEquals(tmpPath, existent.getAbsolutePath());
        }
        assertFalse(tmpDir.exists());
    }
    
    @Test
    public void testDirectoryNotCreatedIfMissingParent() {
    	String tmpPath = "/tmp/L3JocklqYh_BWIX-cgcHM/QzQTAItF7N";
        File tmpDir = new File(tmpPath);
        assertFalse(tmpDir.getParentFile().exists());
        assertFalse(tmpDir.exists());
        try (AutoDeletableDirectory directory = new AutoDeletableDirectory(tmpPath)) {
            try {
            	directory.asExistent();
            	fail("Failure expected");
            }
            catch (Exception e) {
            	assertEquals("Unable to create directory " + tmpPath, e.getMessage());
            	assertFalse(tmpDir.getParentFile().exists());
                assertFalse(tmpDir.exists());
            }
        }
    }
}
