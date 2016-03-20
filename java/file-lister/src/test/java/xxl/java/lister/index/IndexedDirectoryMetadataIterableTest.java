package xxl.java.lister.index;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

import xxl.java.lister.index.IndexedDirectoryMetadataIterable;
import xxl.java.lister.model.DirectoryMetadata;

public class IndexedDirectoryMetadataIterableTest {

	@Test
	public void iterableCannotRemove() {
		Iterable<Integer> ids = asList(1, 2);
		IndexedDirectoryMetadataIterable iterable = new IndexedDirectoryMetadataIterable(ids, null);
		Iterator<DirectoryMetadata> iterator = iterable.iterator();
		try {
			iterator.remove();
			fail();
		}
		catch (UnsupportedOperationException e) {
			return;
		}
	}
}
