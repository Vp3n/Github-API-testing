package github.test;
import junit.framework.Assert;
import github.api.git.Repository;
import github.exception.GithubTimeoutException;
import github.http.GithubHttp;
import github.http.GithubHttpPlay;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import play.test.UnitTest;

public class GithubHttpTest extends UnitTest {
	
	private GithubHttp githubHttp = new GithubHttpPlay();
	
	@Test public void testIsGithubAvailable() {
		Assert.assertTrue(githubHttp.available());
	}
	
	@Test public void testRepositoriesSearch() {
		String response = githubHttp.repositories("playComponent", 1);
		Assert.assertTrue(StringUtils.isNotBlank(response));
	}
	
	@Test public void testContributors() {
		Repository repository = new Repository("playComponent", "vp3n");
		String response = githubHttp.contributors(repository);
		Assert.assertTrue(StringUtils.isNotBlank(response));
	}
	
	@Test public void testCommits() {
		Repository repository = new Repository("playComponent", "vp3n");
		String response = githubHttp.commits(repository, 1);
		Assert.assertTrue(StringUtils.isNotBlank(response));
	}
	

    @Test public void testSearchingWithHugeTimeoutOk() {
    	githubHttp = new GithubHttpPlay(40);
    	try {
    		githubHttp.repositories("play", 1);
    		Assert.assertTrue(true);
    	} catch (GithubTimeoutException e) {
    		Assert.fail("A request with 40 sec timeout should be fine");
    	}
    }
    
    @Test public void testSearchingSpecificTimeout() {
    	githubHttp = new GithubHttpPlay(1);
    	try {
    		githubHttp.repositories("play", 1);
    		Assert.fail("A request with 0 sec timeout should throw an GithubtimeoutException");
    	} catch (GithubTimeoutException e) {
    		Assert.assertTrue(true);
    	}
    }
}