package github.exception;

/**
 * thrown when a timeout error occurs when trying to request 
 * github api
 * @author Vivian Pennel 
 */
public class GithubTimeoutException extends RuntimeException {

	public GithubTimeoutException(String message) {
		super(message);
	}
	
	public GithubTimeoutException(Exception e) {
		super(e);
	}
	
	public GithubTimeoutException(String message, Exception cause) {
		super(message);
		this.initCause(cause);
	}

}
