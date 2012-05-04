package github.parser;

import github.api.git.Commits;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;

import java.util.List;

/**
 * This contract must be implemented to parse
 * github json response and map into api objects. <br />
 * 
 * Implementors should tell which version of the github API they can parse
 * 
 * @see Repositories
 * @see Contributors
 * @see Commits
 * 
 * @author Vivian Pennel
 */
public interface GithubJson {

	/**
	 * convert json repositories datas into {@link Repositories} object
	 * @param repositoriesJson
	 * @return
	 */
	public Repositories parseRepositories(String repositoriesJson);
	
	/**
	 * convert json contributors datas into {@link Contributors} object 
	 * @param contributorsJson
	 * @return
	 */
	public Contributors parseContributors(String contributorsJson);
	
	/**
	 * convert json commits datas into {@link Commits} object
	 * @param commitsJson
	 * @param repository
	 * @return
	 */
	public Commits parseCommits(String commitsJson, Repository repository);
	
	/**
	 * @param json
	 * @return true if json has errors, false otherwise
	 */
	public boolean hasErrors(String json);
	
}
