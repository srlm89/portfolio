package xxl.java.lister.metadata;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;

public class DirectoryMetadataTest extends PathMetadataTest {

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askingHashCodeAndEquality() {
		FileMetadata a = newSingleFile("w", 33L, null);

		DirectoryMetadata w = newDirectory("w", (Iterable) asList(), (Iterable) asList(), null);
		DirectoryMetadata w2 = newDirectory("w", (Iterable) asList(), (Iterable) asList(), w);
		DirectoryMetadata w3 = newDirectory("w", (Iterable) asList(a), (Iterable) asList(), null);
		DirectoryMetadata w4 = newDirectory("w", (Iterable) asList(), (Iterable) asList(w), null);
		DirectoryMetadata w5 = newDirectory("w", (Iterable) asList(a), (Iterable) asList(w), null);
		DirectoryMetadata x = newDirectory("x", (Iterable) asList(), (Iterable) asList(), null);

		assertTrue(w.equals(w2));
		assertTrue(w2.equals(w));

		assertTrue(w.equals(w3));
		assertTrue(w3.equals(w));

		assertTrue(w.equals(w4));
		assertTrue(w4.equals(w));

		assertTrue(w.equals(w5));
		assertTrue(w5.equals(w));

		assertFalse(w.equals(x));
		assertFalse(x.equals(w));

		assertEquals(w.hashCode(), w2.hashCode());
		assertEquals(w.hashCode(), w3.hashCode());
		assertEquals(w.hashCode(), w4.hashCode());
		assertEquals(w.hashCode(), w5.hashCode());

		assertFalse(a.equals(x));
		assertFalse(x.equals(a));
	}
}
