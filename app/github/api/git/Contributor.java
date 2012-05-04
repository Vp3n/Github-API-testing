package github.api.git;

import org.apache.commons.lang.StringUtils;

/**
 * Object representation of a github contributor
 * 
 * @author Vivian Pennel
 */
public class Contributor {
	
	private final String name;
	private final String login;
	private final int totalContributions; 
	
	public Contributor(final String login, final String name, final int totalContributions) {
		this.login = login;
		this.name = StringUtils.isBlank(name) ? login : name;
		this.totalContributions = totalContributions;
	}

	public String getLogin() {
		return login;
	}
	
	public int getTotalContributions() {
		return totalContributions;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 2 contributors are considered equal if they share the
	 * same login
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Contributor)) {
			return false;
		}
		Contributor contributor = (Contributor) obj;
		return contributor.getLogin().equals(this.getLogin());
	}
	
	@Override
	public int hashCode() {
		return login.hashCode();
	}

}
