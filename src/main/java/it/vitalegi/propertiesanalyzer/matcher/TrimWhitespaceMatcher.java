package it.vitalegi.propertiesanalyzer.matcher;

public class TrimWhitespaceMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "Contains whitespaces?";
	}

	@Override
	public boolean matches(String value) {
		if (value == null) {
			return false;
		}
		return value.length() != value.trim().length();
	}

}
