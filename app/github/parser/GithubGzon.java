package github.parser;

import github.api.git.Commit;
import github.api.git.Commits;
import github.api.git.Contributor;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;
import github.exception.GithubParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import play.Logger;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Default implementation of {@link GithubJson}
 * using google Gzon to parse json datas. <br />
 * This implementation supports github api v2
 * 
 * @author Vivian Pennel
 */
public class GithubGzon implements GithubJson {

	@Override
	public Repositories parseRepositories(final String repositoriesJson) {
		Repositories repositories = new Repositories();
		try {
			JsonObject jsonObject = strToJsonObject(repositoriesJson);
			if(hasErrors(repositoriesJson)) {
				return repositories;
			}
			JsonArray jsonRepositories = jsonObject.getAsJsonObject().get("repositories").getAsJsonArray();
			for(JsonElement jsonRepository : jsonRepositories) {
				repositories.add(repository(jsonRepository));
			}
		} catch(Exception e) {
			throw new GithubParseException("Unable to parse " + repositoriesJson, e);
		}
		
		return repositories;
	} 

	@Override
	public Contributors parseContributors(final String contributorsJson) {
		Contributors contributors = new Contributors();
		try {
			JsonObject jsonObject = strToJsonObject(contributorsJson);
			if(hasErrors(contributorsJson)) {
				return contributors;
			}
			JsonArray jsonContribs = jsonObject.getAsJsonObject().get("contributors").getAsJsonArray();
			for(JsonElement el : jsonContribs) {
				contributors.add(contributor(el));
			}
		} catch (Exception e) {
			throw new GithubParseException("Unable to parse " + contributorsJson, e);
		}
		
		return contributors;
	}

	@Override
	public Commits parseCommits(final String commitsJson, final Repository repository) {
		Commits commits = new Commits();
		try {
			JsonObject jsonObject = strToJsonObject(commitsJson);
			if(hasErrors(commitsJson)) {
				return commits;
			}
			JsonArray jsonCommits = jsonObject.getAsJsonObject().get("commits").getAsJsonArray();
			for(JsonElement el : jsonCommits) {
				commits.add(commit(el));
			}
		} catch (Exception e) {
			throw new GithubParseException("Unable to parse " + commitsJson, e);
		}
		
		return commits;
	}
	
	private Commit commit(final JsonElement object) throws ParseException {
		JsonObject commitObject = object.getAsJsonObject();
		
		String message = commitObject.get("message").getAsString();
		String url = commitObject.get("url").getAsString(); 
		Date committed = new DateTime(commitObject.get("committed_date").getAsString()).toDate();
		
		JsonObject committer = commitObject.get("committer").getAsJsonObject();
		Contributor contributor = contributor(committer);
		
		Commit commit = new Commit(committed, message, contributor, url);
		
		return commit;
	}
	
	private Contributor contributor(final JsonElement object) {
		JsonObject jsonObject = object.getAsJsonObject();
		String login = jsonObject.get("login").getAsString();
		String name = jsonObject.get("name") == null ? "" : jsonObject.get("name").getAsString();
		Integer contribs = 0;
		if(jsonObject.get("contributions") != null) {
			String strContribs = jsonObject.get("contributions").getAsString();
			if(strContribs != null) {
				contribs = Integer.parseInt(strContribs);
			}
		}
		
		return new Contributor(login, name, contribs);
	}
	
	private JsonObject strToJsonObject(final String json) {
		return new JsonParser().parse(json).getAsJsonObject();
	}
	
	private Repository repository(final JsonElement object) {
		JsonObject repoJsonObject = object.getAsJsonObject();
		String name = repoJsonObject.get("name").getAsString();
		String owner = repoJsonObject.get("owner").getAsString();
		
		return new Repository(name, owner);
	}

	@Override
	public boolean hasErrors(String errorFixture) {
		return strToJsonObject(errorFixture).get("error") != null;
	}
}
