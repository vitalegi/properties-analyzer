package it.vitalegi.propertiesanalyzer.matcher;

public class LongMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "Checks if the text is an integer number.";
	}

	@Override
	public boolean matches(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
