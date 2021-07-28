package it.vitalegi.propertiesanalyzer.matcher;

import java.net.MalformedURLException;
import java.net.URL;

public class CopyPasteMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "Checks if the text is an URL.";
	}

	@Override
	public boolean matches(String value) {
		try {
			new URL(value);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}

}
