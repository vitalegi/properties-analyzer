package it.vitalegi.propertiesanalyzer;

public class InvalidInputParameterException extends RuntimeException {

	public InvalidInputParameterException() {
		super();
	}

	public InvalidInputParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidInputParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputParameterException(String message) {
		super(message);
	}

	public InvalidInputParameterException(Throwable cause) {
		super(cause);
	}

}
