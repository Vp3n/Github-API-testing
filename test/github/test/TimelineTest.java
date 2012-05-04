package github.test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import github.api.git.Commit;
import github.api.git.Commits;
import github.api.git.Contributor;
import github.api.git.Contributors;
import github.api.stats.Timeline;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class TimelineTest extends UnitTest {
	
	Commits commits = new Commits();
	
	@Before public void setup() {
		Contributor contributors1 = new Contributor("t1", "tn1", 0);
		Contributor contributors2 = new Contributor("t2", "tn2", 0);
		Contributor contributors3 = new Contributor("t3", "tn3", 0);
		Contributor contributors4 = new Contributor("t4", "tn4", 0);
		
		Contributors contributors = new Contributors();
		contributors.add(contributors1);
		contributors.add(contributors2);
		contributors.add(contributors3);
		contributors.add(contributors4);
		
		Commit commit1 = createCommit(contributors1, "23/03/2012");
		Commit commit2 = createCommit(contributors1, "02/04/2012");
		Commit commit3 = createCommit(contributors1, "10/02/2012");
		Commit commit4 = createCommit(contributors2, "15/04/2012");
		Commit commit5 = createCommit(contributors2, "06/03/2012");
		Commit commit6 = createCommit(contributors2, "12/03/2012");
		Commit commit7 = createCommit(contributors2, "01/12/2011");
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);
		commits.add(commit5);
		commits.add(commit6);
		commits.add(commit7);
		
	}
	
	private Commit createCommit(Contributor contributor, String date) {
		Date datec = null;
		try {
			datec = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
		}
		Commit commit = new Commit(datec, "", contributor, "");
		
		return commit;
	}
	
	@Test public void timelineElementOrderedByDateDesc() {
		Timeline timeline = new Timeline(commits);
		
		Assert.assertEquals("15/04/2012", dateToStr(timeline.get(0).getCommitted()));
		Assert.assertEquals("01/12/2011", dateToStr(timeline.get(6).getCommitted()));
	}
	
	@Test public void testFromNowWeek() {
		MutableDateTime date = new MutableDateTime();
		date.addDays(-9);
		Commit commit = new Commit(date.toDate(), null, null, null);
		
		Assert.assertEquals("1 week ago", commit.fromNow());
	}
	
	@Test public void testFromNowSecondes() {
		MutableDateTime date = new MutableDateTime();
		date.addSeconds(-40);
		Commit commit = new Commit(date.toDate(), null, null, null);
		
		Assert.assertEquals("40 secondes ago", commit.fromNow());
	}
	
	private String dateToStr(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

}
