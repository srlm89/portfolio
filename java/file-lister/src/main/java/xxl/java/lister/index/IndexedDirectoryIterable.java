package xxl.java.lister.index;

import java.util.Iterator;


public class IndexedDirectoryIterable implements Iterable<IndexedDirectory> {

	protected IndexedDirectoryIterable(Iterable<Integer> ids, MetadataIndex metadataIndex) {
		this.metadataIndex = metadataIndex;
		this.ids = ids;
	}

	@Override
	public Iterator<IndexedDirectory> iterator() {
		final Iterator<Integer> iterator = ids.iterator();
		return new Iterator<IndexedDirectory>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public IndexedDirectory next() {
				return metadataIndex.directory(iterator.next());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private Iterable<Integer> ids;
	private MetadataIndex metadataIndex;
}
