package github.test;
import junit.framework.Assert;
import github.api.git.Commits;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;
import github.parser.GithubGzon;
import github.parser.GithubJson;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class GithubGzonTest extends UnitTest {
	
	GithubJson githubJson = new GithubGzon();
	String repoFixture;
	String contribFixture;
	String commitFixture;
	String commitFixtureWithoutLogin;
	private String errorFixture;
	
	@Before
	public void setup() {
		repoFixture = "{" +
			"\"repositories\": [ "+
				"{" +
					" \"type\": \"repo\", "+
				    " \"language\": \"Java\", "+
				    " \"url\": \"https://github.com/geemus/fog\", "+
				    " \"homepage\": \"\", "+
				    " \"created_at\": \"2009/05/18 00:14:04 -0700\", "+
				    " \"name\": \"powa\", "+
				    " \"owner\": \"test\", "+
				    " \"description\": \"The Ruby cloud computing library.\" "+
				"}," +
				"{" +
					" \"type\": \"repo\", "+
				    " \"language\": \"Ruby\", "+
				    " \"url\": \"https://github.com/geemus/fog\", "+
				    " \"homepage\": \"\", "+
				    " \"created_at\": \"2009/05/18 00:14:04 -0700\", "+
				    " \"name\": \"fog\", "+
				    " \"owner\": \"geemus\", "+
				    " \"description\": \"The Ruby cloud computing library.\" "+
				"}"+
			"]" +
		"}";	
		
		contribFixture = "{ "+
			  "\"contributors\": [ "+
			    "{"+
			      "\"name\": \"Adam Vandenberg\", "+
			      "\"gravatar_id\": \"7ea0cc75793eb2b1ada4abc953a41592\", "+
			      "\"contributions\": 1367, "+
			      "\"location\": \"Issaquah, WA\", "+
			      "\"blog\": \"http://adamv.com/\", "+
			      "\"type\": \"User\", "+
			      "\"login\": \"adamv\", "+
			      "\"email\": \"flangy@gmail.com\" "+
			    "}"+
			  "]"+
			"}";
		
		commitFixture = "{" +
				"\"commits\": [ "+
					"{"+
						"\"parents\": [ "+
							"{"+
								"\"id\": \"e3be659a93ce0de359dd3e5c3b3b42ab53029065\" "+
        					"}"+
        				"],"+
        				"\"author\": {"+
	        				"\"name\": \"Ryan Tomayko\", "+
	        				"\"login\": \"rtomayko\", "+
	        				"\"email\": \"rtomayko@gmail.com\" "+
      					"},"+
      					"\"url\": \"/mojombo/grit/commit/6b7dff52aad33df4bfc0c0eaa88922fe1d1cd43b\", "+
      					"\"id\": \"6b7dff52aad33df4bfc0c0eaa88922fe1d1cd43b\", "+
      					"\"committed_date\": \"2010-12-09T13:50:17-08:00\","+
      					"\"authored_date\": \"2010-12-09T13:50:17-08:00\", "+
      					"\"message\": \"update History.txt with bug fix merges\", "+
      					"\"tree\": \"a6a09ebb4ca4b1461a0ce9ee1a5b2aefe0045d5f\", "+
      					"\"committer\": { "+
      						"\"name\": \"Ryan Tomayko\", "+
      						"\"login\": \"rtomayko\", "+
      						"\"email\": \"rtomayko@gmail.com\" "+
      					"}"+
    				"}"+
    			"]"+
			"}";
		
		commitFixtureWithoutLogin = "{"+
			    "\"commits\": ["+
			        "{"+
			            "\"parents\": [ {}],"+
			            "\"author\": {"+
			                "\"name\": \"Vivian Pennel\","+
			                "\"login\": \"\", "+
			                "\"email\": \"pennel.vivian+github@gmail.com\" "+
			            "},"+
			            "\"url\": \"/Vp3n/playComponent/commit/47292e64332ae1c829920d03cd7ed69948b56ed0\", "+
			            "\"id\": \"47292e64332ae1c829920d03cd7ed69948b56ed0\", "+
			            "\"committed_date\": \"2012-01-26T13:57:37-08:00\", "+
			            "\"authored_date\": \"2012-01-26T13:57:37-08:00\", "+
			            "\"message\": \"init\", "+
			            "\"tree\": \"f27d888f7bd7e3fb79831f819bd53e83f13e3c5c\", "+
			            "\"committer\": {"+
			                "\"name\": \"Vivian Pennel\","+
			                "\"login\": \"\", "+
			                "\"email\": \"pennel.vivian+github@gmail.com\" "+
			            "}"+
			        "}"+
			    "]"+
			"}";
		
		errorFixture = "{ \"error\" : \"not found\"}";
	}
	
	
	@Test public void parseRepositoriesTest() {
		Assert.assertSame(2, githubJson.parseRepositories(repoFixture).size());
	}
	
	@Test public void testRepositoryObjectWellCreated() {
		Repositories repositories = githubJson.parseRepositories(repoFixture);
		 
		Assert.assertEquals("powa", repositories.get(0).getName());
		Assert.assertEquals("geemus", repositories.get(1).getOwner());
	}
	
	@Test public void parseContributorsTest() {
		Assert.assertEquals(1, githubJson.parseContributors(contribFixture).size());
	}
	
	@Test public void testContributorsObjectWellCreated() {
		Contributors contributors = githubJson.parseContributors(contribFixture);
		 
		Assert.assertEquals("adamv", contributors.first().getLogin());
		Assert.assertEquals("Adam Vandenberg", contributors.first().getName());
		Assert.assertEquals(1367, contributors.first().getTotalContributions());
	}
	
	@Test public void parseCommitsTest() {
		Commits commits = githubJson.parseCommits(commitFixture, new Repository("", ""));
		
		Assert.assertEquals(1, commits.size());
	}
	
	@Test public void testCommitsObjectWellCreated() {
		Commits commits = githubJson.parseCommits(commitFixture, new Repository("", ""));
		
		Assert.assertEquals(1, commits.size());
		Assert.assertEquals(new DateTime("2010-12-09T13:50:17-08:00").toDate(), commits.first().getCommitted());
		Assert.assertEquals("Ryan Tomayko", commits.first().getCommitter().getName());
		Assert.assertEquals("rtomayko", commits.first().getCommitter().getLogin());
	}
	
	@Test public void testCommitWithoutLogin() {
		Commits commits = githubJson.parseCommits(commitFixtureWithoutLogin, new Repository("", ""));
		Assert.assertFalse(commits.isEmpty());
	}
	
	@Test public void testParseErrors() {
		assertTrue(githubJson.hasErrors(errorFixture));
	}
	

}
