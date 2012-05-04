package github.api.git;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import play.Logger;

/**
 * Object representation of a github commit
 * 
 * @author Vivian Pennel
 */
public class Commit {
	
	private final DateTime committed;
	private final String message;
	private final Contributor committer;
	private final String url;
	
	public Commit(Date committed, String message, Contributor committer,
			String url) {
		this.committed = new DateTime(committed);
		this.message = message;
		this.committer = committer;
		this.url = url;
	}
	
	/**
	 * @return A string which display time elapsed since 
	 * this commit has been committed without too many details <br />
	 * 
	 * Example : <ul> 
	 * <li>committed 1 hour and 3 minutes ago, display 1 hour ago</li>
	 * <li>committed 8 days ago, display 1 week ago </li>
	 * </ul>
	 * etc..
	 */
	public String fromNow() {
		Period period = new Period(committed, new DateTime());
		return period.toString(buildFormatter(period));
	}
	
	private PeriodFormatter buildFormatter(Period period) {
		PeriodFormatterBuilder builder = new PeriodFormatterBuilder()
			.printZeroIfSupported()
			.minimumPrintedDigits(0);
	
		if(period.getYears() > 0) {
			builder.appendYears().appendSuffix(" year ago", " years ago");
		} else if (period.getMonths() > 0) {
			builder.appendMonths().appendSuffix(" month ago", " months ago");
		} else if(period.getWeeks() > 0) {
			builder.appendWeeks().appendSuffix(" week ago", " weeks ago");
		} else if (period.getDays() > 0) {
			builder.appendDays().appendSuffix(" day ago", " days ago");
		} else if (period.getHours() > 0) {
			builder.appendHours().appendSuffix(" hour ago", " hours ago");
		} else if(period.getMinutes() > 0) {
			builder.appendMinutes().appendSuffix( "minute ago", " minutes ago");
		} else {
			builder.appendSeconds().appendSuffix(" seconde ago", " secondes ago");
		}
		
		return builder.toFormatter();
	}
	
	public Date getCommitted() {
		return committed.toDate();
	}

	public String getMessage() {
		return message;
	}

	public Contributor getCommitter() {
		return committer;
	}
	
	/**
	 * FIXME : move this URL to a const or routing or so
	 * @return
	 */
	public String getUrl() {
		return "http://github.com" + url;
	}
}
