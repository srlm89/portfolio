package xxl.java.lister.index;

import java.util.Iterator;

import xxl.java.lister.model.DirectoryMetadata;


public class IndexedDirectoryMetadataIterable implements Iterable<DirectoryMetadata> {

	public IndexedDirectoryMetadataIterable(Iterable<Integer> ids, DirectoryMetadataIndex metadataIndex) {
		this.ids = ids;
		this.metadataIndex = metadataIndex;
	}

	@Override
	public Iterator<DirectoryMetadata> iterator() {
		final Iterator<Integer> iterator = ids.iterator();
		return new Iterator<DirectoryMetadata>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public DirectoryMetadata next() {
				return metadataIndex.directory(iterator.next());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private Iterable<Integer> ids;
	private DirectoryMetadataIndex metadataIndex;
}
