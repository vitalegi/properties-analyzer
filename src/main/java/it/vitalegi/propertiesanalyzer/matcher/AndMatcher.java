package it.vitalegi.propertiesanalyzer.matcher;

import java.util.ArrayList;
import java.util.List;

public class AndMatcher implements Matcher {

	List<Matcher> matchers;

	public AndMatcher() {
		matchers = new ArrayList<>();
	}

	public AndMatcher(List<Matcher> matchers) {
		this.matchers = matchers;
	}

	@Override
	public boolean allMatches(List<String> values) {
		for (Matcher matcher : matchers) {
			if (!matcher.allMatches(values)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean anyMatches(List<String> values) {
		for (Matcher matcher : matchers) {
			if (matcher.anyMatches(values)) {
				return true;
			}
		}
		return false;
	}

	public List<Matcher> getMatchers() {
		return matchers;
	}

	public void setMatchers(List<Matcher> matchers) {
		this.matchers = matchers;
	}

}
