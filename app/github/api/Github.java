package github.api;

import github.api.git.Commits;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;
import github.api.stats.UsersImpact;
import github.exception.GithubTimeoutException;
import github.http.GithubHttp;
import github.http.GithubHttpPlay;
import github.parser.GithubGzon;
import github.parser.GithubJson;

import java.util.ArrayList;
import java.util.List;

import play.Logger;


/**
 * Facade to provide access on github API
 * without bothering with implementation <br />
 * 
 * Implementations can be injected using appropriate constructor
 * 
 * @author Vivian Pennel
 */
public class Github {
	
	private final GithubHttp githubHttp;
	private final GithubJson githubJson;
	private static final int MAX_COMMITS_PER_PAGE = 35;
	
	/**
	 * Use this constructor to provide your own implementation
	 * 
	 * @param githubHttp
	 * @param githubJson
	 */
	public Github(final GithubHttp githubHttp, final GithubJson githubJson) {
		this.githubHttp = githubHttp;
		this.githubJson = githubJson;
	}
	
	/**
	 * Use this constructor to use defaults implementations
	 */
	public Github() {
		this(new GithubHttpPlay(), new GithubGzon());
	}

	public Repositories repositories(final String name, int page) {
		String repositoriesJson = githubHttp.repositories(name, page);
		return githubJson.parseRepositories(repositoriesJson);
	}

	public Contributors contributors(final Repository repository) {
		String contributorsJson = githubHttp.contributors(repository);
		return githubJson.parseContributors(contributorsJson);
	}
	
	public Commits commits(final Repository repository, int page) {
		String commitsJson = githubHttp.commits(repository, page);
		return githubJson.parseCommits(commitsJson, repository);
	}
	
	public UsersImpact usersImpactOnLastNCommits(Repository repository, final int n) {
		Contributors contributors = contributors(repository);
    	Commits commits = lastNCommits(repository, n);
    	return new UsersImpact(contributors, commits);
	}
	
	public Commits lastNCommits(final Repository repository, final int n) {
		boolean hasResultsLeft = true;
		Commits commits = new Commits();
		int lastPage = (n / MAX_COMMITS_PER_PAGE) + 2;
		for(int page = 1; page < lastPage && hasResultsLeft; page++) {
			Commits currentPage = commits(repository, page);
			if(currentPage.isEmpty() || currentPage.size() < MAX_COMMITS_PER_PAGE) {
				hasResultsLeft = false;
			}
			commits.addAll(currentPage);
		}
		
		return commits;
	}

}
