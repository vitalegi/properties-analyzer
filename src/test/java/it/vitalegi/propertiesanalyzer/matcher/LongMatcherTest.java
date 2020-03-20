package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class LongMatcherTest {

	@Test
	void testLongIfLongs() {
		assertTrue(new LongMatcher().matches("11"));
	}

	@Test
	void testLongIfNotLongs() {
		assertFalse(new LongMatcher().matches("5.0"));
	}

	@Test
	void testLongIfEmpty() {
		assertFalse(new LongMatcher().matches(""));
	}

	@Test
	void testLongIfNull() {
		assertFalse(new LongMatcher().matches(null));
	}
}
