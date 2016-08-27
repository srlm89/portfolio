package xxl.java.lister.metadata.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import xxl.java.lister.metadata.FileMetadata;
import xxl.java.lister.test.util.TestUtil;

public class FSFileIterableTest extends TestUtil {

	@Test
	public void cannotRemoveFromIterable() {
		File file1 = resource("DirA/DirC/bottom");
		FSPathIterable<FileMetadata> iterable = new FSFileIterable(new File[] { file1 }, null);
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
		File file1 = resource("DirA/DirB/DirD/bottom");
		File file2 = resource("DirA/DirB/DirD/grandson");
		File file3 = resource("DirA/DirB/daughter");
		File file4 = resource("DirA/DirC/bottom");
		File file5 = resource("DirA/dad");
		File file6 = resource("grandpa");
		File[] unordered = new File[] { file6, file4, file2, file5, file3, file1 };
		FSPathIterable<FileMetadata> iterable = new FSFileIterable(unordered, null);
		List<FileMetadata> list = toList(iterable);
		assertEquals(6, list.size());
		assertEquals(file1.getName(), list.get(0).name());
		assertEquals(file2.getName(), list.get(1).name());
		assertEquals(file3.getName(), list.get(2).name());
		assertEquals(file4.getName(), list.get(3).name());
		assertEquals(file5.getName(), list.get(4).name());
		assertEquals(file6.getName(), list.get(5).name());
	}
}
