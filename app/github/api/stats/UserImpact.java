package github.api.stats;

import github.api.git.Contributor;

import java.text.NumberFormat;
import java.util.Comparator;

import play.Logger;

/**
 * Represent an impact in term of number of contributions
 * for a contributor
 * 
 * @author Vivian Pennel
 */
public class UserImpact implements Comparable<UserImpact> {
	
	private final Contributor contributor;
	private final Integer contributions;
	private final Integer contributionsSample;

	/**
	 * @param contributor 
	 * @param contributions total number of contributions 
	 * @param contributionsSample number of contributions for the contributor
	 */
	public UserImpact(Contributor contributor, int contributions, int contributionsSample) {
		this.contributor = contributor;
		this.contributions = contributions;
		this.contributionsSample = contributionsSample;
	}

	/**
	 * estimate a % of impact based on total contributions given in constructor
	 * @return
	 */
	public double getPercentImpactOnSample() {
		if(contributionsSample == 0) {
			return 0;
		}
		
		return ((double)contributions * 100d) / (double)contributionsSample;
	}
	
	public Integer getContributionsSample() {
		return contributionsSample;
	}

	public Integer getImpact() {
		return contributions;
	}
	
	public Contributor getContributor() {
		return contributor;
	}

	/**
	 * Allow to easily sort user impact object by the most impacter first
	 */
	@Override
	public int compareTo(UserImpact userImpact) {
		return userImpact.getImpact().compareTo(contributions);
	}
}
