package xxl.java.lister.test.util;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import xxl.java.io.EasyFile;

public class TestUtil {

	protected File testDir;
	
	@Before
	public void setUp() throws Exception {
		String prefix = getClass().getSimpleName().toLowerCase();
		this.testDir = Files.createTempDirectory("_" + prefix + "_").toFile();
	}
	
	@After
	public void tearDown() {
		EasyFile.delete(testDir);
	}
	
	public static <T> List<T> toList(Iterable<? extends T> iterable) {
		List<T> list = new LinkedList<T>();
		for (T element : iterable) {
			list.add(element);
		}
		return list;
	}
	
	public File builderTest1() {
		return resource("/xxl/java/lister/builder-test-1");
	}
	
	public File resource(String resourceName) {
		URL resource = getClass().getResource(resourceName);
		return new File(resource.getFile());
	}
}
