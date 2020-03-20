package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

public class NotMatcher implements Matcher {

	Matcher matcher;

	public NotMatcher() {
	}

	public NotMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean allMatches(List<String> values) {
		return !matcher.allMatches(values);
	}

	@Override
	public boolean anyMatches(List<String> values) {
		return !matcher.anyMatches(values);
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

}
