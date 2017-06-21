<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/confirmDeleteRecipeDialog.js"></script>
<script type="text/javascript" src="resources/js/recipes/myRecipesController.js"></script>




<div ng-controller="myRecipesController">
	<h2>My Recipes</h2>
	<table id="recipes" border="1">
		<thead><tr>
			<th>ID</th><th>Title</th>
			<th align="right"><a ng-href="recipe.jsp" class="ui-icon ui-icon-create" title="Create a new Recipe"></a></th>
		</tr></thead>
	  	<tbody>
	  		<tr ng-repeat="recipe in myRecipes">
	  			<td>{{recipe.id}}</td>
	  			<td>{{recipe.title}}</td>
	  			<td><a class="ui-icon ui-icon-large ui-icon-edit" ng-href="recipe.jsp?id={{recipe.id}}" title="edit this recipe"></a>
	  				<span class="ui-icon ui-icon-large ui-icon-delete" ng-click="confirmDeleteRecipe(recipe)" title="delete this recipe"></span>
	  			</td>
	  		</tr>
	  	</tbody>
	</table>
	<confirm-delete-recipe-dialog recipe="selectedRecipe" update-func="getMyRecipes()"></confirm-delete-recipe-dialog>
</div>
<jsp:include page="footer.jsp"/>