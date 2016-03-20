package xxl.java.lister.index;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

public class IndexedSingleFileMetadata extends SingleFileMetadata {

	public IndexedSingleFileMetadata(String name, DirectoryMetadata parent) {
		super(name, parent);
	}

	public IndexedSingleFileMetadata withSize(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
		return this;
	}

	@Override
	public long sizeInBytes() {
		return sizeInBytes;
	}

	private long sizeInBytes;
}
