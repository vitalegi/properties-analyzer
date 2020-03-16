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
	public boolean matches(List<String> values) {
		return !matcher.matches(values);
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

}
