package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class BooleanMatcherTest {

	@Test
	void testBooleanIfBoolean() {
		assertTrue(new BoolMatcher().matches("true"));
		assertTrue(new BoolMatcher().matches("false"));
	}

	@Test
	void testBooleanIfNotBoolean() {
		assertFalse(new BoolMatcher().matches("TRUE"));
		assertFalse(new BoolMatcher().matches("aaaaaa"));
	}

	@Test
	void testBooleanIfEmpty() {
		assertFalse(new BoolMatcher().matches(""));
	}

	@Test
	void testBooleanIfNull() {
		assertFalse(new BoolMatcher().matches(null));
	}

}
