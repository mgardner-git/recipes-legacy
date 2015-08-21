<%@page import="org.recipes.web.SessionConstants"%>
<%@page import="org.recipes.model.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="resources/js/angular.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/js/jquery-ui-1.11.4.custom/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="resources/style/recipes.css"/>
<script type="text/javascript" src="resources/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/recipes.js"></script>

<script type="text/javascript">	

	var app = angular.module('recipesApp', []);
	app.controller('RecipesController', function($scope, $http) {
		
	});
</script>
</head>
<body ng-app="recipesApp" ng-controller="RecipesController">
<div id="wrap">
	<div id="header">
	</div>
	<jsp:include page="topNav.jsp"/>
    <div id="leftColumn">
    	<jsp:include page="nav.jsp"/>
    </div>
    <div id="content">
    
    
    
    