package xxl.java.lister.metadata;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;

public class FileMetadataTest extends PathMetadataTest {

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askingHashCodeAndEquality() {
		DirectoryMetadata x = newDirectory("file1.txt", (Iterable) asList(), (Iterable) asList(), null);
		DirectoryMetadata y = newDirectory("folder", (Iterable) asList(), (Iterable) asList(), null);
		FileMetadata a = newSingleFile("file1.txt", 33L, null);
		FileMetadata b = newSingleFile("file1.txt", 33L, x);
		FileMetadata c = newSingleFile("file2.txt", 33L, null);
		FileMetadata d = newSingleFile("file1.txt", 32L, null);
		FileMetadata e = newSingleFile("file2.txt", 32L, null);

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
