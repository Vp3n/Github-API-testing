package controllers;

import play.*;
import play.cache.Cache;
import play.cache.CacheFor;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.*;
import play.mvc.results.Result;

import github.api.Github;
import github.api.git.Commits;
import github.api.git.Contributors;
import github.api.git.Repositories;
import github.api.git.Repository;
import github.api.stats.Timeline;
import github.api.stats.UserImpact;
import github.api.stats.UsersImpact;
import github.exception.GithubTimeoutException;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Application extends Controller {
	
	private final static int IMPACT_NUMBER_OF_COMMITS = 100;
	
    public static void index() {
    	int page = 1;
        render(page);
    }
    
    @CacheFor(value = "5mn")
    public static void info(@NotNull @NotBlank String name, @NotNull @NotBlank String owner) {
    	if(validation.hasError(name)) {
    		notFound();
    	}
    	
    	Repository repository = new Repository(name, owner);
    	Github github = new Github();
    	
    	try {
    		UsersImpact usersImpact = github.usersImpactOnLastNCommits(repository, IMPACT_NUMBER_OF_COMMITS);
    		Timeline timeline = new Timeline(usersImpact.getCommits());
    		render(repository, usersImpact, timeline);
    	} catch (GithubTimeoutException e) {
    		flash.put("error.timeout", e.getMessage());
    		search(repository.getName(), 1);
    	}
    }
    
    @CacheFor(value = "5mn")
    public static void search(@NotNull @NotBlank String name, Integer page) {
    	if(validation.hasErrors()) {
    		flash.error("You must search on keywords");
    		index();
    	}
    	
    	if(page == null || page <= 0) {
    		page = 1;
    	}
    	
    	Github github = new Github();
    	Repositories repositories = new Repositories();
    	try {
    		repositories = github.repositories(name, page);
    	} catch (GithubTimeoutException e) {
    		flash.put("error.timeout", e.getMessage());
    	}
    	
        render("Application/index.html", repositories, page, name);
    }
    
    
    

}