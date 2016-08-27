package xxl.java.lister.main.snapshot;

import static xxl.java.lister.MainUtil.fileWithDatePrefix;
import static xxl.java.lister.metadata.MetadataReader.forDirectoryFromFileSystem;

import java.io.File;
import java.io.IOException;

import xxl.java.lister.UsageException;
import xxl.java.lister.index.zip.MetadataZipSnapshot;
import xxl.java.lister.metadata.DirectoryMetadata;

public class SnapshotMaker {

	public static void makeFor(String directoryPath) throws UsageException, IOException {
		DirectoryMetadata metadata = forDirectoryFromFileSystem(directoryPath);
		String suffix = metadata.name() + ".zip";
		File snapshotFile = fileWithDatePrefix(suffix);
		new MetadataZipSnapshot(snapshotFile.getAbsolutePath(), metadata);
	}
}
