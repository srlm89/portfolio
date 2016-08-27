package xxl.java.lister.main.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static xxl.java.io.EasyIO.newReader;
import static xxl.java.lister.metadata.fs.FileSystemMetadata.withRoot;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import xxl.java.lister.main.comparator.DirectoryDifference;
import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.test.util.TestUtil;

public class DirectoryDifferenceTest extends TestUtil {

	@Test
	public void differenceTest1() throws IOException {
		File leftFolder = resource("difference-test-1/left");
		File rightFolder = resource("difference-test-1/right");
		DirectoryDifference difference = difference(leftFolder, rightFolder);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		difference.showComparison(output);
		BufferedReader reader = newReader(output.toByteArray());

		assertEquals(".", reader.readLine());
		assertEquals("<<<  jizxn/", reader.readLine());
		assertEquals(">>>  nxzij/", reader.readLine());
		assertEquals("<--  wazoo.txt (12)", reader.readLine());
		assertEquals("-->  wazoo.txt (16)", reader.readLine());
		assertEquals("", reader.readLine());

		assertEquals("./madsiq", reader.readLine());
		assertEquals("<--  j0f9as.txt (9)", reader.readLine());
		assertEquals("-->  j0f9as.txt (18)", reader.readLine());
		assertEquals("", reader.readLine());

		assertEquals("./madsiq/MNI", reader.readLine());
		assertEquals("-->  skoq.txt (6)", reader.readLine());
		assertEquals("", reader.readLine());

		assertEquals("./masco", reader.readLine());
		assertEquals("<--  wazoo.txt (18)", reader.readLine());
		assertEquals("-->  wazoo.txt (20)", reader.readLine());
		assertEquals("", reader.readLine());

		assertEquals(null, reader.readLine());
		output.close();
		reader.close();
	}

	@Test
	public void flushAfterComparison() throws IOException {
		File leftFolder = resource("difference-test-1/left");
		File rightFolder = resource("difference-test-1/right");
		DirectoryDifference difference = difference(leftFolder, rightFolder);
		final boolean[] flushed = new boolean[] { false };
		ByteArrayOutputStream output = new ByteArrayOutputStream() {
			@Override
			public void flush() throws IOException {
				super.flush();
				flushed[0] = true;
			}
		};
		difference.showComparison(output);
		assertTrue(flushed[0]);
		output.close();
	}

	private DirectoryDifference difference(File a, File b) {
		DirectoryMetadata first = (DirectoryMetadata) withRoot(a);
		DirectoryMetadata second = (DirectoryMetadata) withRoot(b);
		return new DirectoryDifference(first, second);
	}
}
