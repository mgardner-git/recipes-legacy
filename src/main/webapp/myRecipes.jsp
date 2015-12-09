<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>

<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	app.controller('myRecipesController', function($scope, $http) {
		$scope.getMyRecipes = function(){
			$http.get("rest/recipes/myRecipes").
			success(function(data, status, headers, config) {
				$scope.myRecipes = data;
			});
		}
		$scope.getMyRecipes();
	});
</script>



<div ng-controller="myRecipesController">
	<h2>My Recipes</h2>
	<table id="recipes" border="1">
		<thead><tr>
			<th>ID</th><th>Title</th>
			<th><a ng-href="recipe.jsp" class="ui-icon ui-icon-create" title="Create a new Recipe"></a></th>
		</tr></thead>
	  	<tbody>
	  		<tr ng-repeat="recipe in myRecipes">
	  			<td>{{recipe.id}}</td>
	  			<td>{{recipe.title}}</td>
	  			
	  		</tr>
	  	</tbody>
	</table>
</div>
<jsp:include page="footer.jsp"/>