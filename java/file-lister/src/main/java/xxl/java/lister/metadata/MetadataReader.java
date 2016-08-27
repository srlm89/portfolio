package xxl.java.lister.metadata;

import static xxl.java.io.EasyFile.isValidDirectoryPath;
import static xxl.java.io.EasyFile.isValidFilePath;
import static xxl.java.lister.metadata.fs.FileSystemMetadata.withRoot;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import xxl.java.lister.UsageException;
import xxl.java.lister.index.zip.ZipMetadataIndex;

public class MetadataReader {

	public static DirectoryMetadata forDirectoryFromFileSystem(String directoryPath) throws UsageException {
		File directory = directory(directoryPath);
		PathMetadata metadata = withRoot(directory);
		return (DirectoryMetadata) metadata;
	}
	
	private static File directory(String directoryPath) throws UsageException {
		if (isValidDirectoryPath(directoryPath)) {
			return new File(directoryPath);
		}
		throw new UsageException("Invalid directory: " + directoryPath);
	}

	public static DirectoryMetadata forDirectoryFromSnapshot(String snapshotFile) throws UsageException {
		ZipFile zipFile = snapshotFile(snapshotFile);
		ZipMetadataIndex index = new ZipMetadataIndex(zipFile);
		return index.root();
	}
	
	private static ZipFile snapshotFile(String snapshotFile) throws UsageException {
		if (isValidFilePath(snapshotFile)) {
			try {
				return new ZipFile(snapshotFile);
			}
			catch (IOException e) {/**/}
		}
		throw new UsageException("Invalid snapshot file: " + snapshotFile);
	}
}
