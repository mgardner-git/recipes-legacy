<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript" src="/recipes/resources/js/angular.min.js"></script>
<script type="text/javascript" src="/recipes/resources/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="/recipes/resources/js/jquery-ui-1.11.4.custom/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="/recipes/resources/style/recipes.css"/>
<link rel="stylesheet" type="text/css" href="/recipes/resources/js/ngTooltips-master/css/ng-tooltips.css"/>
<link rel="stylesheet" type="text/css" href="/recipes/resources/js/jquery-ui-timepicker-addon.css"/>
<script type="text/javascript" src="/recipes/resources/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/recipes/resources/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/recipes/resources/js/recipes.js"></script>
<script type="text/javascript" src="/recipes/resources/js/ngTooltips-master/js/ngtooltips.js"></script>
<script type="text/javascript" src="/recipes/resources/js/directives/date-time.js"></script>
<title>Recipes</title>
</head>
<body ng-app="recipesApp" ng-controller="recipesController">
<div id="wrap">
	<header>
		<h1>Recipes</h1>
	</header>
	<jsp:include page="topNav.jsp"/>
    <div id="leftColumn">
    	<jsp:include page="nav.jsp"/>
    </div>
    <div id="content">
    
    
    
    