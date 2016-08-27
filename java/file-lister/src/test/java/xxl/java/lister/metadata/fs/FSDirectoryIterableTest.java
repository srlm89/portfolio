package xxl.java.lister.metadata.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import xxl.java.lister.test.util.TestUtil;

public class FSDirectoryIterableTest extends TestUtil {

	@Test
	public void cannotRemoveFromIterable() {
		File directoryA = resource("DirA");
		FSPathIterable<FSDirectoryMetadata> iterable = new FSDirectoryIterable(new File[] { directoryA }, null);
		try {
			iterable.iterator().remove();
			fail();
		}
		catch (UnsupportedOperationException e) {
			return;
		}
	}

	@Test
	public void filesSortedInIterable() {
		File directoryA = resource("DirA");
		File directoryB = resource("DirA/DirB");
		File directoryD = resource("DirA/DirB/DirD");
		File directoryC = resource("DirA/DirC");
		File[] unordered = new File[] { directoryC, directoryB, directoryA, directoryD };
		FSPathIterable<FSDirectoryMetadata> iterable = new FSDirectoryIterable(unordered, null);
		List<FSDirectoryMetadata> list = toList(iterable);
		assertEquals(4, list.size());
		assertEquals(directoryA.getName(), list.get(0).name());
		assertEquals(directoryB.getName(), list.get(1).name());
		assertEquals(directoryD.getName(), list.get(2).name());
		assertEquals(directoryC.getName(), list.get(3).name());
	}
}
