<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/measurementTypeDialog.js"></script>
<script type="text/javascript" src="resources/js/directives/ingredientDialog.js"></script>

<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	var id = parseLocation()["id"];
	console.log(id);
	app.controller('recipeController', function($scope, $http,$timeout) {
		
		
		$scope.loadMeasurementTypes = function(){
			$http.get("rest/measurementTypes").
			success(function(data,status,headers,config){
				$scope.measurementTypes = data;
			});
		};
		
		$scope.loadRecipe = function(){
			$http.get("rest/recipes/" + id).
			success(function(data, status, headers, config) {
				$scope.recipe = data;
			});			
		};
		
		$scope.configureQuantities = function(){
			var rows = jQuery("#ingredients tbody tr");		
			quantityInputs = rows.find("td:first input");
			quantityInputs.spinner();
			
		};
		
		$scope.configureMeasurementTypes = function(){
			var rows = jQuery("#ingredients tbody tr");
			var measurementTypeInputs = rows.find("td:nth-of-type(2) input");				
			measurementTypeInputs.each(function(index,inputNode){
				inputNode = jQuery(inputNode);
				inputNode.autocomplete({
					source: function(searchObj, response){
						var results = [];
						for (var index=0; index < $scope.measurementTypes.length; index++){
							//if the title of the measurementType object begins with the entered search term
							var mtObj = $scope.measurementTypes[index];
							if (mtObj.label.toUpperCase().indexOf(searchObj.term.toUpperCase()) == 0){
								var match = $scope.measurementTypes[index];
								results.push(match);
							}
						}
						response(results);
						
					},
					select: function(event,ui){
						var mt = ui.item.value;							
						$scope.recipe.recipeUsesIngredients[index].measurementType = mt;
						event.preventDefault();
						inputNode.val(ui.item.label);							
					},
					change: function(event,ui){
						//if the user types something in, but it doesn't match to anything
						if (!ui.item){
							var mt = {
									title: inputNode.val(),
									description: ""
							};
							$scope.recipe.recipeUsesIngredients[index].measurementType = mt;
						}
					}
				});//end autocomplete config
			});//end each
		};
		
		$scope.configureIngredients = function(){
			var rows = jQuery("#ingredients tbody tr");		
			var ingredientInputs = rows.find("td:nth-of-type(3) input");
			
			ingredientInputs.each(function(index,inputNode){
				inputNode = jQuery(inputNode);				
				inputNode.autocomplete({
					source: "rest/ingredients",
					select: function(event,ui){
						var ingredient = ui.item.value;
						$scope.recipe.recipeUsesIngredients[index].ingredient=ingredient;
						event.preventDefault();
						inputNode.val(ui.item.label);
					},
					change: function(event,ui){
						//if the user types something in, but it doesn't match to anything
						if (!ui.item){
							var ingredient = {
									title: inputNode.val(),
									description: ""
							};
							$scope.recipe.recipeUsesIngredients[index].ingredient = ingredient;
							/**
							TODO: This still leaves open the possibility that the user has typed in an exact match to another ingredient not knowing that he has to select it from the menu.
							*/
						}
					}
				});				
			});
		};
		
		$scope.openAddMeasurementTypeDialog = function(){
			jQuery("#measurementTypeDialog").dialog("open");
		};
		
		$scope.openAddIngredientDialog = function(){
			jQuery("#ingredientDialog").dialog("open");
		}
		
		$scope.addRui = function(){
			$scope.recipe.recipeUsesIngredients.push({});
		}
		
		$scope.removeRui = function(rui){
			var index = $scope.recipe.recipeUsesIngredients.indexOf(rui);
			$scope.recipe.recipeUsesIngredients.splice(index,1);
		};
		
		$scope.saveRecipe = function(){
			var recipeStr = JSON.stringify($scope.recipe);

			$http({
				method: $scope.recipe.id ? "PUT":"POST",
				url: "rest/recipes",
				data: recipeStr				
			}).then(function(response){
				alert("Recipe Saved");
				window.location.href="myRecipes.jsp";				
			});
		}
		
		$scope.$watch("recipe.recipeUsesIngredients",function(){			
			$timeout(function(){
				$scope.configureQuantities();
				$scope.configureMeasurementTypes();
				$scope.configureIngredients();
			});//end timeout
		},true);
		
		$scope.measurementTypes = [];
		$scope.loadMeasurementTypes();
		if (id!=null){
			$scope.loadRecipe();
		}else{
			$scope.recipe = {
				recipeUsesIngredients: []
			};
		}
		
	});
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
				<td><input name="quantity" ng-model="rui.quantity"/></td>
				<td><input name="measurementType" ng-model="rui.measurementType.title"/></td>
				<td><input name="ingredient" ng-model="rui.ingredient.title"/></td>
				<td><span ng-click="removeRui(rui)" class="ui-icon ui-icon-large ui-icon-delete" title="Remove this ingredient from the recipe"></span></td>
			</tr>	
		</tbody>
	</table>
	<measurement-type-dialog update-func="loadMeasurementTypes()"></measurement-type-dialog>
	<ingredient-dialog></ingredient-dialog>
	<input type="button" ng-click="saveRecipe()" value="Save Recipe"></input>
</div>
<jsp:include page="footer.jsp"/>