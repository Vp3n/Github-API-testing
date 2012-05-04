jQuery(function($) {
	
	"use strict"
	
	
	// @param config 
	//  {
	// 	searchField: id
	//  enableAjax: boolean
	//  ajaxRender: id
	//  repositoriesContainer: id
	// 	repositoryElement: class
	//  enablePreloading: boolean
	//  paginationParam : string
	//  enablePopover : boolean
	//  popoverContainer : class
	//  enableColumns: boolean
	//  columns: int
	// }
	githubAPI.App = function(config) {
		var config = config
		
		//caching elements
		var searchField = $("#"+config.searchField)
		var ajaxRender = $("#"+config.ajaxRender)
		var repositoriesContainer = $("#"+config.repositoriesContainer)
		var router = githubAPI.router()
		var repositoriesList = $("."+config.repositoryElement);
		
		// private methods
		var that = this;

		that.enableFeatures = function() {
			if(config.enableColumns) {
				this.columnize()
			}
			if(config.enablePopover) {
				this.addPopover()
			}
			if(config.enablePreloading) {
				this.preload()
			}
		}
		
		that.addListeners = function() {
			if(config.enableAjax) {
				this.ajaxify()
			}
		}

		that.getColumnsWidth = function(columns) {
			if(columns === 0) return 100
			return 99 / columns
		}
		
		that.createColumnElement = function(columns) {
			return $("<div>")
				.attr("class", "column")
				.attr("style","width: " + that.getColumnsWidth(columns)+"%")
		}
		
		// load asynchronously the next search page 
		this.preload = function() {
			var name = searchField.val()
			var page = $.url().param(config.paginationParam)
			
			if(undefined === page) {
				page = 2
			} else {
				page++
			}
			
			if(undefined !== name && "" !== name) {
			  $.get(router.repositories({name: name, page: page}))
			}
		}
		
		// tranform search form into ajax form
		this.ajaxify = function() {
			
			var form = searchField.closest("form")
			
			form.on("submit", function(event) {
				
				event.preventDefault()
				var name = searchField.val()
				
				$.get(router.repositories({name: name, page: 1}), function(datas) {
					ajaxRender.html($(datas).find("#"+config.repositoriesContainer));
					that.enableFeatures()
				})
			})
		}
		
		// display search results in n columns
		this.columnize = function() {
			if(config.columns == 0) return
			
			var elements = $("."+config.repositoryElement)
			var size = elements.length
			var currentWrap = 0
			var maxPerColumns = parseInt(size / config.columns) + 1
			
			elements.each(function(i, el) {
				// we wrap only if element is not first
				if(i % maxPerColumns === 0 && i > 0 || i === (size - 1)) {
					// if we are on last element we also take it in wrapper 
					var wrappedElements = elements.slice(currentWrap, i === (size-1) ? i+1 : i)
					
					$(wrappedElements).wrapAll(that.createColumnElement(config.columns))
					currentWrap = i
				}
			});
		}
		
		// add a toolip on each repository link
		this.addPopover = function() {
			repositoriesList.find("a").each(function() {
				$(this).popover({
					title: $(this).text(),
					content: "Owner -> " + $(this).next("."+config.popoverContainer).text()
				});
			});
		}
		
		that.enableFeatures()
		that.addListeners()
	}
	
	
	var app = new githubAPI.App({
		searchField: "name",
		enableAjax: false,
		ajaxRender: "repositories-wrapper",
		repositoriesContainer: "repositories",
		repositoryElement: "repository",
		enablePreloading: true,
		paginationParam : "page",
		enablePopover : true,
		popoverContainer : "owner",
		enableColumns: true,
		columns: 4
	});
});