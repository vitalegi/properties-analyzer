package it.vitalegi.propertiesanalyzer.matcher;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlMatcher extends AbstractMatcher {

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
