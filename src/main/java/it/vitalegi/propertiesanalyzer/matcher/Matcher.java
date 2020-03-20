package it.vitalegi.propertiesanalyzer.matcher;

import java.util.Arrays;
import java.util.List;

public interface Matcher {

	static List<Matcher> matchers() {
		return Arrays.asList( //
				new AbsolutePathMatcher(), //
				new BoolMatcher(), //
				new FileExtensionMatcher(), //
				new LongMatcher(), //
				new DoubleMatcher(), //
				new TrimWhitespaceMatcher(), //
				new UrlMatcher(), //
				new NotEmptyStringMatcher());
	}

	boolean allMatches(List<String> values);

	boolean anyMatches(List<String> values);

	boolean matches(String value);

	boolean anyNotMatches(List<String> values);

	String name();

	String description();
}
