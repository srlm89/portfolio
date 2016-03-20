package xxl.java.lister.model;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

public class SingleFileMetadataTest extends FileMetadataTest {

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askingHashCodeAndEquality() {
		DirectoryMetadata x = newDirectory("file1.txt", (Iterable) asList(), (Iterable) asList(), null);
		DirectoryMetadata y = newDirectory("folder", (Iterable) asList(), (Iterable) asList(), null);
		SingleFileMetadata a = newSingleFile("file1.txt", 33L, null);
		SingleFileMetadata b = newSingleFile("file1.txt", 33L, x);
		SingleFileMetadata c = newSingleFile("file2.txt", 33L, null);
		SingleFileMetadata d = newSingleFile("file1.txt", 32L, null);
		SingleFileMetadata e = newSingleFile("file2.txt", 32L, null);

		assertTrue(a.equals(b));
		assertTrue(b.equals(a));

		assertFalse(a.equals(c));
		assertFalse(c.equals(a));

		assertFalse(a.equals(d));
		assertFalse(d.equals(a));

		assertFalse(a.equals(e));
		assertFalse(e.equals(a));

		assertEquals(a.hashCode(), b.hashCode());

		assertFalse(a.equals(x));
		assertFalse(x.equals(a));

		assertFalse(a.equals(y));
		assertFalse(y.equals(a));
	}
}
