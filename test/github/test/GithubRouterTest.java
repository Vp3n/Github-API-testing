package github.test;
import junit.framework.Assert;
import github.api.git.Repository;
import github.http.GithubRouter;
import github.http.GithubRouterImpl;

import org.junit.Test;

import play.test.UnitTest;

public class GithubRouterTest extends UnitTest {
	
	GithubRouter githubRouter = new GithubRouterImpl();
	
	@Test public void urlRepositoriesFor() {
		String url = githubRouter.repositories("play Component", 1);
		
		Assert.assertEquals("http://github.com/api/v2/json/repos/search/play+Component?start_page=1", url);
	}
	
	@Test public void urlContributorsFor() {
		Repository repository = new Repository("playComponent", "vp3n");
		String url = githubRouter.contributors(repository);
		
		Assert.assertEquals("http://github.com/api/v2/json/repos/show/vp3n/playComponent/contributors", url);
	}
	
	@Test public void urlCommitsFor() {
		Repository repository = new Repository("playComponent", "vp3n");
		String url = githubRouter.commits(repository, 1);
		
		Assert.assertEquals("http://github.com/api/v2/json/commits/list/vp3n/playComponent/master?page=1", url);
	}
	
	// github api crash on routing when a dot is on keywords
	@Test public void dotShouldBeReplacedBySpace() {
		String url = githubRouter.repositories("play.Component", 1);
		
		Assert.assertEquals("http://github.com/api/v2/json/repos/search/play+Component?start_page=1", url);
	}

}
