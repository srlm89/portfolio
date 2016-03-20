package xxl.java.lister;

import static xxl.java.lister.fs.FileSystemMetadataBuilder.fileMetadataFor;

import java.io.File;
import java.util.zip.ZipFile;

import xxl.java.lister.index.zip.ZipDirectoryMetadataIndex;
import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.FileMetadata;

public class MetadataCreator {

	public static DirectoryMetadata forDirectoryFromFileSystem(String directoryPath) throws UsageException {
		File directory = MainUtil.directory(directoryPath);
		FileMetadata metadata = fileMetadataFor(directory);
		return (DirectoryMetadata) metadata;
	}

	public static DirectoryMetadata forDirectoryFromSnapshot(String snapshotFile) throws UsageException {
		ZipFile zipFile = MainUtil.snapshotFile(snapshotFile);
		ZipDirectoryMetadataIndex index = new ZipDirectoryMetadataIndex(zipFile);
		return index.root();
	}
}
