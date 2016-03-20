package xxl.java.lister;

import static xxl.java.lister.MetadataCreator.forDirectoryFromFileSystem;
import static xxl.java.lister.MetadataCreator.forDirectoryFromSnapshot;

import java.io.File;
import java.io.IOException;

import xxl.java.io.EasyIO;
import xxl.java.lister.exe.RegexFileList;
import xxl.java.lister.model.DirectoryMetadata;

public class FileLister {

	public static void fromFileSystem(String directoryPath, String regex, String replacement) throws UsageException,
			IOException {
		DirectoryMetadata metadata = forDirectoryFromFileSystem(directoryPath);
		RegexFileList fileList = new RegexFileList(metadata, regex, replacement);
		File outputFile = MainUtil.fileWithDatePrefix("yyyy-MM-dd-HH-mm-ss", "_", "lister.txt");
		fileList.print(EasyIO.writerFor(outputFile));
	}

	public static void fromSnapshot(String snapshotFile, String regex, String replacement) throws UsageException,
			IOException {
		DirectoryMetadata metadata = forDirectoryFromSnapshot(snapshotFile);
		RegexFileList fileList = new RegexFileList(metadata, regex, replacement);
		File outputFile = MainUtil.fileWithDatePrefix("yyyy-MM-dd-HH-mm-ss", "_", "lister.txt");
		fileList.print(EasyIO.writerFor(outputFile));
	}
}
