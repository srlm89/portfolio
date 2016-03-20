package xxl.java.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.reflection.OperatingSystem.isMac;
import static xxl.java.reflection.OperatingSystem.isSolaris;
import static xxl.java.reflection.OperatingSystem.isUnix;
import static xxl.java.reflection.OperatingSystem.isWindows;
import static xxl.java.reflection.OperatingSystem.simpleName;

import org.junit.Ignore;
import org.junit.Test;

public class OperatingSystemTest {

	@Test
	@Ignore("Platform-dependent")
	public void simpleNameInThisLaptop() {
		assertTrue(isUnix());
		assertFalse(isMac());
		assertFalse(isSolaris());
		assertFalse(isWindows());
		assertEquals("unix", simpleName());
	}
}
