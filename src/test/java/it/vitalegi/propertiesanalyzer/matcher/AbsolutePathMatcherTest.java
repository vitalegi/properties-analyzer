package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class AbsolutePathMatcherTest {

	@Test
	void testFilePathIfUnixPath() {
		assertTrue(new AbsolutePathMatcher().matches("/path/to/file.txt"));
	}

	@Test
	void testFilePathIfUnixRelativePath() {
		assertFalse(new AbsolutePathMatcher().matches("path/to/file.txt"));
	}

	@Test
	void testFilePathIfWindowsPath() {
		assertTrue(new AbsolutePathMatcher().matches("C:\\path\\to\\file.txt"));
	}

	@Test
	void testFilePathIfWindowsRelativePath() {
		assertFalse(new AbsolutePathMatcher().matches("path\\to\\file.txt"));
	}

	@Test
	void testFilePathIfNotPath() {
		assertFalse(new AbsolutePathMatcher().matches("aaaa"));
	}
}
