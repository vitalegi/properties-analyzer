package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class TrimWhitespaceMatcherTest {

	@Test
	void testTrimWhitespaceIfWhitespacePrefix() {
		assertTrue(new TrimWhitespaceMatcher().matches(" aaa"));
	}

	@Test
	void testTrimWhitespaceIfWhitespaceSuffix() {
		assertTrue(new TrimWhitespaceMatcher().matches("aaa "));
	}

	@Test
	void testTrimWhitespaceIfNoWhitespace() {
		assertFalse(new TrimWhitespaceMatcher().matches("aaa"));
	}
}
