package github.api.git;

import github.api.util.GithubList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Commits extends GithubList<Commit> {
	
	public Commit first() {
		return get(0);
	}
	
	public Contributors getCommitters() {
		Contributors contributors = new Contributors();
		for(Commit commit : this) {
			contributors.add(commit.getCommitter());
		}
		
		return contributors;
	}
}
