package github.test;
import org.junit.*;

import github.api.Github;
import github.api.git.Commits;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;
import github.exception.GithubTimeoutException;

import java.util.*;
import play.test.*;

public class GithubAPITest extends UnitTest {
	
	private final Github github = new Github();
    
	@Test public void testSearchingReturnResults() {
        Repositories repositories = github.repositories("play", 1);
        
        Assert.assertNotNull(repositories);
        Assert.assertNotSame(0, repositories.size());
    }
	
    @Test public void testSearchingReturnSpecificResult() {
    	Repositories repositories = github.repositories("play Component", 1);
        
        Assert.assertEquals("playComponent", repositories.get(0).getName());
    }
    
    @Test public void testContributors() { 
    	Repository repository = new Repository("play20", "playframework");
    	Contributors contributors = github.contributors(repository);
    	
    	Assert.assertFalse(contributors.isEmpty());
    	Assert.assertEquals("Peter Hausel", contributors.first().getName());
    }
    
    @Test public void testCommits() {
    	Repository repository = new Repository("playComponent", "vp3n");
    	Commits commits = github.commits(repository, 1);
    	
    	Assert.assertFalse(commits.isEmpty());
    	Assert.assertEquals("init", commits.first().getMessage());
    }
    
    @Test public void testBugParsing() {
    	Repository repository = new Repository("skeleton_wp", "simplethemes");
    	Commits commits = github.commits(repository, 1);
    	
    	
    	Assert.assertTrue(commits.size() > 2);
    }

}
