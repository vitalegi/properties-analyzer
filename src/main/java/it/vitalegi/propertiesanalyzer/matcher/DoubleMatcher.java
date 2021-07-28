package it.vitalegi.propertiesanalyzer.matcher;

public class DoubleMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "is a decimal number?";
	}

	@Override
	public boolean matches(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}

}
