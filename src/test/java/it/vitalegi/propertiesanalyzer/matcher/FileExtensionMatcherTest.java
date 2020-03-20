package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class FileExtensionMatcherTest {

	@Test
	void testFileExtensionIfExtension() {
		assertTrue(new FileExtensionMatcher().matches("a.txt"));
	}

	@Test
	void testFileExtensionIfNoExtension() {
		assertFalse(new FileExtensionMatcher().matches("aaaa"));
	}

	@Test
	void testFileExtensionIfNonValidChar() {
		assertFalse(new FileExtensionMatcher().matches("aa.txt;"));
	}

	@Test
	void testFileExtensionIfNull() {
		String str = null;
		assertFalse(new FileExtensionMatcher().matches(str));
	}

	@Test
	void testFileExtensionIfEmpty() {
		assertFalse(new FileExtensionMatcher().matches(""));
	}
}
