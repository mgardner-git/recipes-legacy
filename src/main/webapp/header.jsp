<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/recipes.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}resources/node_modules/bootstrap/dist/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/node_modules/ng-number-spin/dist/css/ng-number-spin.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/node_modules/angular-ui-bootstrap/dist/ui-bootstrap-csp.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular/angular.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular-animate/angular-animate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/ng-number-spin/dist/js/ng-number-spin.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/recipes.js"></script>


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
    
    
    
    