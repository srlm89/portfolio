package xxl.java.lister;

import static java.lang.String.format;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainUtil {

	public static File fileWithDatePrefix(String suffix) {
		return dateFormatFile("yyyy-MM-dd", "_", suffix);
	}
	
	public static File fileWithHourPrefix(String suffix) {
		return dateFormatFile("yyyy-MM-dd-HH-mm-ss", "_", suffix);
	}
	
	private static File dateFormatFile(String dateFormat, String separator, String suffix) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String filePrefix = format.format(new Date());
		return new File(format("%s%s%s", filePrefix, separator, suffix));
	}
}
