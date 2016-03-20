package xxl.java.lister.exe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import xxl.java.lister.exe.RegexFileList;
import xxl.java.lister.fs.FileSystemMetadataBuilder;
import xxl.java.lister.model.FileMetadata;
import xxl.java.lister.test.util.TestUtil;

public class RegexFileListTest extends TestUtil {

	@Test
	public void txtFiles() {
		RegexFileList lister = newFileLister("^.*/([^/]+)[.]txt$", "$1");
		Map<String, Integer> fileList = lister.fileList();
		assertEquals(10, fileList.size());
		assertTrue(4 == fileList.get("d10j"));
		assertTrue(1 == fileList.get("1241"));
		assertTrue(1 == fileList.get("masp"));
		assertTrue(1 == fileList.get("qiojf"));
		assertTrue(1 == fileList.get("213kpo"));
		assertTrue(1 == fileList.get("21512"));
		assertTrue(1 == fileList.get("fqiqw"));
		assertTrue(1 == fileList.get("cmqq"));
		assertTrue(1 == fileList.get("vqiqni"));
		assertTrue(1 == fileList.get("csoamw"));
	}

	@Test
	public void musicFiles() {
		RegexFileList lister = newFileLister("^/([^/]*)/(.*/)?([^/]+)(mp3|wav)$", "$1:$3$4");
		Map<String, Integer> fileList = lister.fileList();
		assertEquals(4, fileList.size());
		assertTrue(2 == fileList.get("file-lister-1:d10j.mp3"));
		assertTrue(1 == fileList.get("file-lister-1:2124.mp3"));
		assertTrue(1 == fileList.get("file-lister-1:d10j.wav"));
		assertTrue(1 == fileList.get("file-lister-1:9103.wav"));
	}

	@Test
	public void d10jFiles() {
		RegexFileList lister = newFileLister("^.*(d10j)(.*)$", "$1$2");
		Map<String, Integer> fileList = lister.fileList();
		assertEquals(3, fileList.size());
		assertTrue(4 == fileList.get("d10j.txt"));
		assertTrue(2 == fileList.get("d10j.mp3"));
		assertTrue(1 == fileList.get("d10j.wav"));
	}

	private RegexFileList newFileLister(String regex, String replacement) {
		File folder = resource("file-lister-1");
		FileMetadata metadata = FileSystemMetadataBuilder.fileMetadataFor(folder);
		return new RegexFileList(metadata, regex, replacement);
	}
}
