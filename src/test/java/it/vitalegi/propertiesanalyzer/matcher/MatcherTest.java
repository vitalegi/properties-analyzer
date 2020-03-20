package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class MatcherTest {
	@Test
	void testAllEqualsIfEquals() {
		List<String> values = Arrays.asList("value", "value");
		assertTrue(new EqualMatcher().allMatches(values));
	}

	@Test
	void testAllEqualsIfNotEquals() {
		List<String> values = Arrays.asList("value", "different value");
		assertFalse(new EqualMatcher().allMatches(values));
	}

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

	@Test
	void testLongIfLongs() {
		assertTrue(new LongMatcher().matches("11"));
	}

	@Test
	void testLongIfNotLongs() {
		assertFalse(new LongMatcher().matches("5.0"));
	}

	@Test
	void testBooleanIfBoolean() {
		assertTrue(new BoolMatcher().matches("true"));
		assertTrue(new BoolMatcher().matches("false"));
	}

	@Test
	void testBooleanIfNotBoolean() {
		assertFalse(new LongMatcher().matches("TRUE"));
		assertFalse(new LongMatcher().matches("aaaaaa"));
	}

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

	@Test
	void testNotMatcherIfTrue() {
		assertFalse(new NotMatcher(dummyMatcher(true)).allMatches(Arrays.asList("aaa")));
	}

	@Test
	void testNotMatcherIfFalse() {
		assertTrue(new NotMatcher(dummyMatcher(false)).allMatches(Arrays.asList("aaa")));
	}

	@Test
	void testOrMatcher() {
		assertTrue(new OrMatcher(dummyMatchers(true, true)).allMatches(Arrays.asList("aaa")));
		assertTrue(new OrMatcher(dummyMatchers(true, false)).allMatches(Arrays.asList("aaa")));
		assertTrue(new OrMatcher(dummyMatchers(false, true)).allMatches(Arrays.asList("aaa")));
		assertFalse(new OrMatcher(dummyMatchers(false, false)).allMatches(Arrays.asList("aaa")));
	}

	@Test
	void testAndMatcher() {
		assertTrue(new AndMatcher(dummyMatchers(true, true)).allMatches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(true, false)).allMatches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(false, true)).allMatches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(false, false)).allMatches(Arrays.asList("aaa")));
	}

	protected Matcher dummyMatcher(boolean output) {
		Matcher matcher = mock(Matcher.class);
		when(matcher.allMatches(any())).thenReturn(output);
		return matcher;
	}

	protected List<Matcher> dummyMatchers(boolean... outputs) {
		List<Matcher> matchers = new ArrayList<>();
		for (boolean out : outputs) {
			matchers.add(dummyMatcher(out));
		}
		return matchers;
	}
}
