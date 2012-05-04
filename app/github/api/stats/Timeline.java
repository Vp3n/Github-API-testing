package github.api.stats;

import github.api.git.Commit;
import github.api.git.Commits;
import github.api.util.GithubList;

import java.util.Collections;
import java.util.Comparator;

/**
 * Allow to sort commit ordered by committed date desc 
 * 
 * @author Vivian Pennel
 */
public class Timeline extends GithubList<Commit> {
	
	public Timeline(Commits commits) {
		addAll(commits);
		Collections.sort(this, new Comparator<Commit>() {
			
			@Override
			public int compare(Commit c1, Commit c2) {
				return c2.getCommitted().compareTo(c1.getCommitted());
			}
			
		});
	}

}
