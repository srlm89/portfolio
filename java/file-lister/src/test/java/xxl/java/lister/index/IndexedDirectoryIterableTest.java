package xxl.java.lister.index;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

public class IndexedDirectoryIterableTest {

	@Test
	public void iterableCannotRemove() {
		Iterable<Integer> ids = asList(1, 2);
		IndexedDirectoryIterable iterable = new IndexedDirectoryIterable(ids, null);
		Iterator<IndexedDirectory> iterator = iterable.iterator();
		try {
			iterator.remove();
			fail();
		}
		catch (UnsupportedOperationException e) {
			return;
		}
	}
}
