package github.http;

import github.api.git.Repository;
import github.exception.GithubTimeoutException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import play.Logger;
import play.libs.WS;
import play.libs.F.Promise;
import play.libs.WS.HttpResponse;
import play.mvc.Http;

/**
 * Default implementation of {@link GithubHttp}
 * use playframework {@link WS} to provide access over http. <br />
 * This implementation is synchronous and each method call
 * can throw an {@link GithubTimeoutException}
 * 
 * @see GithubTimeoutException
 * @see GithubHttp
 * @see WS
 * @author Vivian Pennel
 */
public class GithubHttpPlay implements GithubHttp {
	
	private final static int DEFAULT_TIMEOUT = 20;
	private final static TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	private final GithubRouter router;
	private final int timeout;
	
	/**
	 * Use this constructor to provide your own routing implementation
	 * 
	 * @param githubRouter
	 * @param timeout in sec
	 */
	public GithubHttpPlay(final GithubRouter githubRouter, int timeout) {
		this.router = githubRouter;
		this.timeout = timeout;
	}
	
	/**
	 * Default constructor, using default timeout and 
	 * default implementation of router to provide api URLs
	 */
	public GithubHttpPlay() {
		this(DEFAULT_TIMEOUT);
	}
	
	/**
	 * @param timeout in sec
	 */
	public GithubHttpPlay(int timeout) {
		this.router = new GithubRouterImpl();
		this.timeout = timeout;
	}
	
	@Override
	public String repositories(final String name, int page) {
		return asyncRequestWrappingErrors(router.repositories(name, page), timeout);
	}
	
	@Override
	public boolean available() {
		Promise<HttpResponse> promise = async(router.baseURL());
		return hasWrapErrors(promise, DEFAULT_TIMEOUT);
	}

	@Override
	public String contributors(final Repository repository) {
		return asyncRequestWrappingErrors(router.contributors(repository), timeout);
	}
	
	@Override
	public String commits(final Repository repository, int page) {
		return asyncRequestWrappingErrors(router.commits(repository, page), timeout);
	}
	
	private String asyncRequestWrappingErrors(final String url, final int timeout) {
		Logger.info("Request gihub for " +url+ " with timeout of " + timeout +" secondes");
		Promise<HttpResponse> promise = async(url);
		HttpResponse response = null;
		try {
			response = getWrapErrors(promise, timeout);
			return response.getString();
		} catch (Exception e) {
			throw new GithubTimeoutException("request took more than " + timeout + " to finish", e);
		}
	}
	
	private HttpResponse getWrapErrors(final Promise<HttpResponse> promise, final int timeout) throws InterruptedException,
		ExecutionException, TimeoutException {
		
		HttpResponse response;
		response = promise.get(timeout, TIMEOUT_UNIT);
		
		return response;
	}
	
	private boolean hasWrapErrors(final Promise<HttpResponse> promise, final int timeout) {
		try {
			getWrapErrors(promise, timeout);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private Promise<HttpResponse> async(final String url) {
		return WS.url(url).getAsync();
	}
}
