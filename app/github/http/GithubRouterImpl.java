package github.http;

import github.api.git.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import org.apache.commons.lang.StringUtils;

import play.libs.WS;

/**
 * default implementation of {@link GithubRouter}.   
 * use playframework {@link WS} to encode URL parameters. <br />
 * 
 * This implementation provide URL to access github api V2
 * 
 * @see WS
 * @author Vivian Pennel
 */
public class GithubRouterImpl implements GithubRouter {
	
	enum Routes {
		BASE("http://github.com/api/v2"),
		REPOSITORIES("http://github.com/api/v2/json/repos/search/{name}?start_page={page}"),
		CONTRIBUTORS("http://github.com/api/v2/json/repos/show/{owner}/{name}/contributors"),
		COMMITS("http://github.com/api/v2/json/commits/list/{owner}/{name}/{branch}?page={page}");
		
		private final String route;
		
		Routes(String route) {
			this.route = route;
		}
		public String url() {
			return route;
		}
	}
	
	@Override
	public String repositories(final String name, int page) {
		String url = Routes.REPOSITORIES.url();
		//api does not support '.' for repositories searching
		String cleanedName = StringUtils.replace(name, ".", " ");
		url = replaceVar(url, "page", String.valueOf(page));
		return replaceVar(url, "name", cleanedName);
	}

	@Override
	public String contributors(final Repository repository) {
		String url = Routes.CONTRIBUTORS.url();
		url = replaceRepositoryVars(repository, url);
		
		return url;
	}

	@Override
	public String commits(final Repository repository, int page) {
		String url = Routes.COMMITS.url();
		url = replaceRepositoryVars(repository, url);
		url = replaceVar(url, "page", String.valueOf(page));
		url = replaceVar(url, "branch", "master");
		
		return url;
	}
	
	@Override
	public String baseURL() {
		return Routes.BASE.url();
	}

	
	private String replaceRepositoryVars(final Repository repository, final String url) {
		String toReplace = new String(url);
		toReplace = replaceVar(toReplace, "owner", repository.getOwner());
		toReplace = replaceVar(toReplace, "name", repository.getName());
		
		return toReplace;
	}
	
	private String replaceVar(final String url, final String var, final String value) {
		return StringUtils.replace(url, "{"+var+"}", WS.encode(value));
	}
}
