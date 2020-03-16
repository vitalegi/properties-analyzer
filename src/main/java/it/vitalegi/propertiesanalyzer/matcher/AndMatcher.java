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
	public boolean matches(List<String> values) {
		for (Matcher matcher : matchers) {
			if (!matcher.matches(values)) {
				return false;
			}
		}
		return true;
	}

	public List<Matcher> getMatchers() {
		return matchers;
	}

	public void setMatchers(List<Matcher> matchers) {
		this.matchers = matchers;
	}

}
