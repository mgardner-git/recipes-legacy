<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/measurementTypeDialog.js"></script>
<script type="text/javascript" src="resources/js/directives/ingredientDialog.js"></script>
<script type="text/javascript" src="resources/js/recipes/recipesController.js"></script>

<script type="text/javascript">
	
</script>



<div ng-controller="recipeController">
	<h2>Recipe</h2>
	<p>
		<label>Name:</label>
		<input name="title" ng-model="recipe.title"/>
	</p>
	<p>
		<label>Instructions:</label>
		<textarea name="instructions" ng-model="recipe.instructions"></textarea>
	</p>
	<h3>Ingredients</h3>
	<table border="1" id="ingredients" width="570">
		<thead><tr>
			<th class="numericHeader">Quantity</th>
			<!-- 
			<a class="ui-icon ui-icon-create" ng-click="openAddMeasurementTypeDialog()" title="Add a new type of measurement"></a>
			<a class="ui-icon ui-icon-create" ng-click="openAddIngredientDialog()" title="Add a new ingredient to the system"></a>
			 -->
			<th>Measurement </th>
			<th>Ingredient </th>
			<th><a class="ui-icon ui-icon-create" ng-click="addRui()" title="Select a new quantity and ingredient for this recipe"></a></th>
		</tr></thead>
		<tbody>
			<tr ng-repeat="rui in recipe.recipeUsesIngredients">
				<td><input name="quantity" ng-model="rui.quantity" numeric/></td>
				<td><input name="measurementType" ng-model="rui.measurementType.title"/></td>
				<td><input name="ingredient" ng-model="rui.ingredient.title"/></td>
				<td><span ng-click="removeRui(rui)" class="ui-icon ui-icon-large ui-icon-delete" title="Remove this ingredient from the recipe"></span></td>
			</tr>	
		</tbody>
	</table>
	<measurement-type-dialog update-func="loadMeasurementTypes()"></measurement-type-dialog>
	<ingredient-dialog></ingredient-dialog>
	<input type="button" ng-click="saveRecipe()" value="Save Recipe" ng-disabled="submitDisabled()"></input>
</div>
<jsp:include page="footer.jsp"/>