package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class UrlMatcherTest {

	@Test
	void testUrlsIfUrl() {
		assertTrue(new UrlMatcher().matches("https://localhost/aaa?q=123"));
		assertTrue(new UrlMatcher().matches("https://192.0.0.1/aaa"));
		assertTrue(new UrlMatcher().matches("https://google.com/"));
	}

	@Test
	void testUrlsIfNotUrl() {
		assertFalse(new UrlMatcher().matches("test"));
	}
}
