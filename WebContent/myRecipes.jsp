<%@page import="web.SessionConstants"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript">
	
	var app = angular.module('Recipes', []);
	app.controller('RecipesController', function($scope, $http) {
		$scope.myRecipes = eval('<%=request.getAttribute(SessionConstants.MY_RECIPES) %>');
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Recipes</title>
</head>
<body ng-app="Recipes" ng-controller="RecipesController">



	  <table id="recipes">
	  	<thead><tr><th>ID</th><th>Title</th></tr></thead>
	  	<tbody>
	  		<tr ng-repeat="recipe in myRecipes">
	  			<td>{{recipe.id}}</td>
	  			<td>{{recipe.title}}</td>
	  		</tr>
	  	</tbody>
	  </table>
	  
	  
		
</body>
</html>