package xxl.java.lister.index;

import xxl.java.lister.metadata.FileMetadata;

public class IndexedFile extends FileMetadata {

	public IndexedFile(String name, IndexedDirectory parent) {
		super(name, parent);
	}

	public IndexedFile withSize(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
		return this;
	}

	@Override
	public long sizeInBytes() {
		return sizeInBytes;
	}

	private long sizeInBytes;
}
