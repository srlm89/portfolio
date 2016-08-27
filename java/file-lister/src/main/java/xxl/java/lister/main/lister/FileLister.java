package xxl.java.lister.main.lister;

import static xxl.java.lister.MainUtil.fileWithHourPrefix;
import static xxl.java.lister.metadata.MetadataReader.forDirectoryFromFileSystem;
import static xxl.java.lister.metadata.MetadataReader.forDirectoryFromSnapshot;

import java.io.File;
import java.io.IOException;

import xxl.java.io.EasyIO;
import xxl.java.lister.UsageException;
import xxl.java.lister.metadata.DirectoryMetadata;

public class FileLister {

	public static void fromFileSystem(String directoryPath, String regex, String replacement) throws UsageException, IOException {
		DirectoryMetadata metadata = forDirectoryFromFileSystem(directoryPath);
		listFiles(metadata, regex, replacement);
	}

	public static void fromSnapshot(String snapshotFile, String regex, String replacement) throws UsageException, IOException {
		DirectoryMetadata metadata = forDirectoryFromSnapshot(snapshotFile);
		listFiles(metadata, regex, replacement);
	}
	
	private static void listFiles(DirectoryMetadata metadata, String regex, String replacement) throws IOException {
		RegexFileList fileList = new RegexFileList(metadata, regex, replacement);
		File outputFile = fileWithHourPrefix("lister.txt");
		fileList.print(EasyIO.writerFor(outputFile));
	}
}
