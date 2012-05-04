package github.test;
import java.util.Date;

import junit.framework.Assert;

import github.api.git.Commit;
import github.api.git.Commits;
import github.api.git.Contributor;
import github.api.git.Contributors;
import github.api.stats.UsersImpact;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class UserImpactTest extends UnitTest {
	
	private Commits commits = new Commits();
	private Contributors contributors = new Contributors();
	
	@Before public void setup() {
		Contributor contributors1 = new Contributor("t1", "tn1", 0);
		Contributor contributors2 = new Contributor("t2", "tn2", 0);
		Contributor contributors3 = new Contributor("t3", "tn3", 0);
		Contributor contributors4 = new Contributor("t4", "tn4", 0);
		
		contributors.add(contributors1);
		contributors.add(contributors2);
		contributors.add(contributors3);
		contributors.add(contributors4);
		
		Commit commit1 = new Commit(new Date(), "", contributors1, "");
		Commit commit2 = new Commit(new Date(), "", contributors1, "");
		Commit commit3 = new Commit(new Date(), "", contributors1, "");
		Commit commit4 = new Commit(new Date(), "", contributors2, "");
		Commit commit5 = new Commit(new Date(), "", contributors2, "");
		Commit commit6 = new Commit(new Date(), "", contributors3, "");
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);
		commits.add(commit5);
		commits.add(commit6);
	}
	
	@Test public void contributorsOrderedByImpact() {
		UsersImpact usersImpact = new UsersImpact(contributors, commits);
		
		Assert.assertTrue(usersImpact.get(0).getImpact() > usersImpact.get(1).getImpact());
		Assert.assertTrue(usersImpact.get(1).getImpact() > usersImpact.get(2).getImpact());
	}
	
	@Test public void contributorsWithoutCommitExistWith0() {
		UsersImpact usersImpact = new UsersImpact(contributors, commits);
		Assert.assertTrue(usersImpact.get(3).getImpact().equals(0));
	}
	
	@Test public void contributionsPercent() {
		UsersImpact usersImpact = new UsersImpact(contributors, commits);
		Assert.assertEquals(50d, usersImpact.get(0).getPercentImpactOnSample());
		
		//33,3333333...
		Assert.assertTrue(33d < usersImpact.get(1).getPercentImpactOnSample() && 
				34d > usersImpact.get(1).getPercentImpactOnSample());
		
		// 16,66666...
		Assert.assertTrue(16d < usersImpact.get(2).getPercentImpactOnSample() && 
				17d > usersImpact.get(2).getPercentImpactOnSample());
		
		//0
		Assert.assertEquals(0d, usersImpact.get(3).getPercentImpactOnSample());
	}
}
