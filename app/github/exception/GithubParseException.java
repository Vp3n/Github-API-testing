package github.exception;

import play.Logger;

/**
 * Thrown when a parsing error occurs (mapping json to java objects for example)
 * @author Vivian Pennel
 */
public class GithubParseException extends RuntimeException {
	
	public GithubParseException(String message) {
		super(message);
	}

	public GithubParseException(String message, Exception e) {
		super(message, e);
		Logger.error(e, "");
	}

}
