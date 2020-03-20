package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class DoubleMatcherTest {

	@Test
	void testDoubleIfDouble() {
		assertTrue(new DoubleMatcher().matches("5.0"));
	}

	@Test
	void testDoubleIfLong() {
		assertTrue(new DoubleMatcher().matches("10"));
	}

	@Test
	void testDoubleIfEmpty() {
		assertFalse(new DoubleMatcher().matches(""));
	}

	@Test
	void testDoubleIfNull() {
		assertFalse(new DoubleMatcher().matches(null));
	}
}
