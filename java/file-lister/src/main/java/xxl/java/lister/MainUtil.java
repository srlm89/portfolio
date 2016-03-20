package xxl.java.lister;

import static java.lang.String.format;
import static xxl.java.io.EasyFile.isValidDirectoryPath;
import static xxl.java.io.EasyFile.isValidFilePath;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipFile;

public class MainUtil {

	public static ZipFile snapshotFile(String snapshotFile) throws UsageException {
		if (isValidFilePath(snapshotFile)) {
			try {
				return new ZipFile(snapshotFile);
			}
			catch (IOException e) {/**/}
		}
		throw new UsageException("Invalid snapshot file: " + snapshotFile);
	}

	public static File directory(String directoryPath) throws UsageException {
		if (isValidDirectoryPath(directoryPath)) {
			return new File(directoryPath);
		}
		throw new UsageException("Invalid directory: " + directoryPath);
	}

	public static File fileWithDatePrefix(String dateFormat, String separator, String suffix) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String filePrefix = format.format(new Date());
		return new File(format("%s%s%s", filePrefix, separator, suffix));
	}
}
