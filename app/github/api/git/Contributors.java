package github.api.git;

import github.api.util.GithubList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.collection.AbstractCollectionDecorator;

public class Contributors extends GithubList<Contributor> {

	public Contributor first() {
		return get(0);
	}
}
