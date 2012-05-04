package github.http;

import github.api.git.Repository;

/**
 * This contract can be implemented 
 * to provide api URLs using a {@link GithubHttp} implementation. <br />
 * 
 * Implementors should be aware to correctly encode URL parameters
 * @see GithubHttp
 *  
 * @author Vivian Pennel
 */
public interface GithubRouter {
	
	/**
	 * @param name
	 * @param page
	 * @return url to look up repositories with a name and a page 
	 */
	public String repositories(String name, int page);
	
	/**
	 * @param repository
	 * @return url to look up contributors for a repository
	 */
	public String contributors(Repository repository);
	
	/**
	 * @param repository
	 * @param page
	 * @return url to look up commits for a repository with a page
	 */
	public String commits(Repository repository, int page);
	
	/**
	 * @return base url of github api
	 */
	public String baseURL();

}
