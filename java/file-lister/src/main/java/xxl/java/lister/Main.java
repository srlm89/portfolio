package xxl.java.lister;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import xxl.java.io.EasyIO;
import xxl.java.lister.main.comparator.MetadataComparator;
import xxl.java.lister.main.lister.FileLister;
import xxl.java.lister.main.snapshot.SnapshotMaker;

public class Main {

	public static void main(String[] args) throws IOException {
		try {
			String action = parse(args, 0, asList("-list", "-snapshot", "-compare"));
			if (action.equals("-list")) {
				listFiles(args);
			}
			if (action.equals("-snapshot")) {
				snapshot(args);
			}
			if (action.equals("-compare")) {
				compare(args);
			}
		}
		catch (UsageException e) {
			System.out.println('[' + e.getLocalizedMessage() + ']');
			showUsage();
		}
	}

	private static void listFiles(String[] args) throws UsageException, IOException {
		String type = parse(args, 1, asList("-fs", "-s"));
		String path = parse(args, 2, null);
		String regex = parse(args, 3, null);
		String replacement = parse(args, 4, null);
		if (type.equals("-fs")) {
			FileLister.fromFileSystem(path, regex, replacement);
		}
		if (type.equals("-s")) {
			FileLister.fromSnapshot(path, regex, replacement);
		}
	}

	private static void snapshot(String[] args) throws UsageException, IOException {
		String path = parse(args, 1, null);
		SnapshotMaker.makeFor(path);
	}

	private static void compare(String[] args) throws UsageException, IOException {
		String type = parse(args, 1, asList("-fs", "-s"));
		String pathA = parse(args, 2, null);
		String pathB = parse(args, 3, null);
		if (type.equals("-fs")) {
			MetadataComparator.fromFileSystem(pathA, pathB);
		}
		if (type.equals("-s")) {
			MetadataComparator.fromSnapshots(pathA, pathB);
		}
	}

	private static String parse(String[] args, int i, Collection<String> valid) throws UsageException {
		if (args.length > i) {
			String option = args[i];
			if (valid == null || valid.contains(option)) {
				return option;
			}
		}
		throw new UsageException("Invalid arguments");
	}

	private static void showUsage() throws IOException {
		InputStream resource = Main.class.getResourceAsStream("/usage.txt");
		String lines = EasyIO.asSequence(resource);
		System.out.println(lines);
	}
}
