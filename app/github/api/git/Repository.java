package github.api.git;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representation of a github repository
 * 
 * @author Vivian Pennnel
 */
public class Repository {
	
	private final String name;
	
	private final String owner;
	
	public Repository(String name, String owner) {
		this.name = name;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}
}
