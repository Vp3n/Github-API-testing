package github.api.stats;

import github.api.git.Commits;
import github.api.git.Contributor;
import github.api.git.Contributors;
import github.api.util.GithubList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import play.Logger;

/**
 * 
 * This class calculate impact of each contributors on 
 * a list of commits, both injected
 * 
 * @author Vivian Pennel
 */
public class UsersImpact extends GithubList<UserImpact> {
	
	private final Commits commits;
	private final Contributors contributors;
	
	public UsersImpact(final Contributors contributors, final Commits commits) {
		this.commits = commits;
		this.contributors = contributors;
		impact();		
		Collections.sort(this);
	}
	
	private void impact() {
		int commitsSample = commits.size();
		Contributors committers = commits.getCommitters();
		for(Contributor contributor : contributors) {
			UserImpact userImpact = new UserImpact(contributor, 
					Collections.frequency(committers, contributor), commitsSample);
			add(userImpact);
		}
	}

	public Commits getCommits() {
		return commits;
	}

	public Contributors getContributors() {
		return contributors;
	}
}
