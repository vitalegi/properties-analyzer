package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class NotEmptyStringMatcherTest {

	@Test
	void testNotEmptyStringIfEmpty() {
		assertFalse(new NotEmptyStringMatcher().matches(""));
	}

	@Test
	void testNotEmptyStringIfNull() {
		assertFalse(new NotEmptyStringMatcher().matches(null));
	}

	@Test
	void testNotEmptyStringIfWhitespace() {
		assertFalse(new NotEmptyStringMatcher().matches(" "));
	}

	@Test
	void testNotEmptyStringIfNotEmpty() {
		assertTrue(new NotEmptyStringMatcher().matches("text"));
	}
}
