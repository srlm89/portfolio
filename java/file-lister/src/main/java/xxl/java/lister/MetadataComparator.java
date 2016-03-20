package xxl.java.lister;

import static xxl.java.lister.MetadataCreator.forDirectoryFromFileSystem;
import static xxl.java.lister.MetadataCreator.forDirectoryFromSnapshot;

import java.io.File;
import java.io.IOException;

import xxl.java.io.EasyIO;
import xxl.java.lister.exe.DirectoryDifference;
import xxl.java.lister.model.DirectoryMetadata;

public class MetadataComparator {

	public static void fromSnapshots(String snapshotFileA, String snapshotFileB) throws UsageException, IOException {
		DirectoryMetadata snapshotA = forDirectoryFromSnapshot(snapshotFileA);
		DirectoryMetadata snapshotB = forDirectoryFromSnapshot(snapshotFileB);
		File outputFile = MainUtil.fileWithDatePrefix("yyyy-MM-dd-HH-mm-ss", "_", "comparison.txt");
		new DirectoryDifference(snapshotA, snapshotB).showComparison(EasyIO.outputStreamFor(outputFile));
	}

	public static void fromFileSystem(String directoryPathA, String directoryPathB) throws UsageException, IOException {
		DirectoryMetadata directoryA = forDirectoryFromFileSystem(directoryPathA);
		DirectoryMetadata directoryB = forDirectoryFromFileSystem(directoryPathB);
		File outputFile = MainUtil.fileWithDatePrefix("yyyy-MM-dd-HH-mm-ss", "_", "comparison.txt");
		new DirectoryDifference(directoryA, directoryB).showComparison(EasyIO.outputStreamFor(outputFile));
	}
}
