package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class CopyPasteMatcherTest {

	@Test
	void testWhenAllDifferentShouldNotTrigger() {
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a")));
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a", "b")));
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a", "b", "c")));
	}

	@Test
	void testWhenAllEqualsShouldNotTrigger() {
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a")));
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a", "a")));
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a", "a", "a")));
		assertFalse(new CopyPasteMatcher().trigger(Arrays.asList("a", "a", "a", "a")));
	}

	@Test
	void testWhenOneDifferentShouldTrigger() {
		assertTrue(new CopyPasteMatcher().trigger(Arrays.asList("a", "a", "b")));
	}
}
