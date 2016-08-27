package xxl.java.lister.main.comparator;

import static xxl.java.lister.MainUtil.fileWithHourPrefix;
import static xxl.java.lister.metadata.MetadataReader.forDirectoryFromFileSystem;
import static xxl.java.lister.metadata.MetadataReader.forDirectoryFromSnapshot;

import java.io.File;
import java.io.IOException;

import xxl.java.io.EasyIO;
import xxl.java.lister.UsageException;
import xxl.java.lister.metadata.DirectoryMetadata;

public class MetadataComparator {

	public static void fromSnapshots(String snapshotFileA, String snapshotFileB) throws UsageException, IOException {
		DirectoryMetadata snapshotA = forDirectoryFromSnapshot(snapshotFileA);
		DirectoryMetadata snapshotB = forDirectoryFromSnapshot(snapshotFileB);
		File outputFile = fileWithHourPrefix("comparison.txt");
		new DirectoryDifference(snapshotA, snapshotB).showComparison(EasyIO.outputStreamFor(outputFile));
	}

	public static void fromFileSystem(String directoryPathA, String directoryPathB) throws UsageException, IOException {
		DirectoryMetadata directoryA = forDirectoryFromFileSystem(directoryPathA);
		DirectoryMetadata directoryB = forDirectoryFromFileSystem(directoryPathB);
		File outputFile = fileWithHourPrefix("comparison.txt");
		new DirectoryDifference(directoryA, directoryB).showComparison(EasyIO.outputStreamFor(outputFile));
	}
}
