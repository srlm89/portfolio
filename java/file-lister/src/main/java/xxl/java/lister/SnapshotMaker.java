package xxl.java.lister;

import static xxl.java.lister.MetadataCreator.forDirectoryFromFileSystem;

import java.io.File;
import java.io.IOException;

import xxl.java.lister.index.zip.FileMetadataSnapshot;
import xxl.java.lister.model.DirectoryMetadata;

public class SnapshotMaker {

	public static void makeFor(String directoryPath) throws UsageException, IOException {
		DirectoryMetadata metadata = forDirectoryFromFileSystem(directoryPath);
		File snapshotFile = MainUtil.fileWithDatePrefix("yyyy-MM-dd", "_", metadata.name() + ".zip");
		new FileMetadataSnapshot(snapshotFile.getAbsolutePath(), metadata);
	}
}
