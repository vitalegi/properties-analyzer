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
				new NotEmptyStringMatcher(), //
				new CopyPasteMatcher());
	}

	public boolean trigger(List<String> values);

	String name();

	String description();
}
