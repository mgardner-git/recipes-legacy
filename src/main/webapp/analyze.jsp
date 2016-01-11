<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>

<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	app.controller('analyzeController', function($scope, $http, $timeout) {
		$scope.selectedRecipe = null;
		$scope.getMyRecipes = function(){
			$http.get("rest/recipes/myRecipes").
			success(function(data, status, headers, config) {
				$scope.myRecipes = data;
			});
		}
		
		$scope.getMyRecipes();
	});
</script>

<div ng-controller="analyzeController">
	<ul>
		<li><a href="analyzeMyIngredients.jsp">Look for recipes with ingredients similar to the ingredients in my recipes</a></li>
		<li><a href="analyzeIngredients.jsp">Look for recipes with a set of ingredients that I select</a></li>
		
	</ul>
</div>

<jsp:include page="footer.jsp"/>