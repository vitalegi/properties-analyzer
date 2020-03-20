package it.vitalegi.propertiesanalyzer.matcher;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import it.vitalegi.propertiesanalyzer.util.StringUtil;

public class AbsolutePathMatcher extends AbstractMatcher {

	@Override
	public String description() {
		return "Checks if the text could be an absolute path, both for windows or unix.";
	}

	@Override
	public boolean matches(String value) {
		if (StringUtil.isNullOrEmpty(value)) {
			return false;
		}
		try {
			Paths.get(value);
		} catch (InvalidPathException e) {
			return false;
		}
		if (value.startsWith("/")) {
			return true;
		}
		if (value.substring(1).startsWith(":\\")) {
			return true;
		}
		return false;
	}
}
