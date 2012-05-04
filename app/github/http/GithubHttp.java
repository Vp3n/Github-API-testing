package github.http;

import github.api.git.Repository;

/**
 * this contract must be implemented to
 * provide github api access over http
 *  
 * @author Vivian Pennel
 */
public interface GithubHttp {

	/**
	 * Search for repositories based on a name
	 * pagination can change based on implementation (api v2 or v3 for example)
	 * @param name
	 * @param page 
	 * @return raw response of github api. Implementors should choose the format (xml, json..) 
	 */
	public String repositories(String name, int page);
	
	/**
	 * @return true if api is available, false otherwise
	 */
	public boolean available();
	
	/**
	 * search contributors for a repository
	 * this method must retrieve all contributors 
	 * @param repository
	 * @return raw response of github api. Implementors should choose the format (xml, json..)
	 */
	public String contributors(Repository repository);
	
	
	/**
	 * search commits for a repository
	 * pagination can change based on implementation (api v2 or v3 for example)
	 * @param repository
	 * @param page
	 * @return raw response of github api. Implementors should choose the format (xml, json..)
	 */
	public String commits(Repository repository, int page);

}
