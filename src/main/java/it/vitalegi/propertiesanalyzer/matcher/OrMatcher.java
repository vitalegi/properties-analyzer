package it.vitalegi.propertiesanalyzer.matcher;

import java.util.ArrayList;
import java.util.List;

public class OrMatcher implements Matcher {

	List<Matcher> matchers;

	public OrMatcher() {
		matchers = new ArrayList<>();
	}

	public OrMatcher(List<Matcher> matchers) {
		this.matchers = matchers;
	}

	@Override
	public boolean allMatches(List<String> values) {
		for (Matcher matcher : matchers) {
			if (matcher.allMatches(values)) {
				return true;
			}
		}
		return false;
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
