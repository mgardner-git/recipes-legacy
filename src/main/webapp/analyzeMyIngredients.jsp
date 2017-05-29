<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/selector.js"></script>
<script type="text/javascript" src="resources/js/directives/ng-size.js"></script>
<script type="text/javascript" src="resources/js/directives/numeric.js"></script>
<script type="text/javascript">
	
	
	
	app.controller('analyzeController', function($scope, $http, $timeout) {
		$scope.myIngredients = []; //ingredients in recipes owned by this user
		$scope.selectedIngredients=[];  //a selected subset of myIngredients
		/**
		Recipes will have all recipes whose list of ingredients does not contain more then {{threshold}} ingredients that are not in selected ingredients
		*/
		$scope.threshold=1; 
		$scope.recipes = [];
		$scope.connectedIngredients; //all ingredients in $scope.recipes ((help the user see what else he might need to buy))
		$scope.analyzeIngredients = function(){
			//translate from the autcomplete format back to ingredient model objects
			var ingredients = [];
			for (var index=0; index < $scope.selectedIngredients.length; index++){
				ingredients.push($scope.selectedIngredients[index].value);
			}

			var ingredientsStr = JSON.stringify(ingredients);
			jQuery.ajax({
				url: "rest/recipes/analyzeIngredients?threshold=" + $scope.threshold,
				type: "POST",
				data: ingredientsStr, 
				contentType: "application/json",
				success: function(data){
					$scope.recipes = data;
					$timeout(function(){
						$scope.updateConnectedIngredients();
						$timeout(function(){  //I don't know why I need this 2nd timeout							
							$scope.formatColumns();
							$scope.drawLines();
						});
					});
				}
			});
		};
		
		
		//create the list of all ingredients within recipes found by the cupboard algorithm. This will populate the 3rd column
		$scope.updateConnectedIngredients = function(){
			var ingredientList = [];
			for (var index=0; index < $scope.recipes.length; index++){
				var recipe = $scope.recipes[index];
				for (var ruiIndex=0; ruiIndex < recipe.recipeUsesIngredients.length; ruiIndex++){
					var checkIngredient = recipe.recipeUsesIngredients[ruiIndex].ingredient;
					//avoid duplication
					var found = false;
					for (var ingredientIndex=0; ingredientIndex < ingredientList.length; ingredientIndex++){
						if (ingredientList[ingredientIndex].id==checkIngredient.id){
							found = true;
						}
					}
					if (!found){
						ingredientList.push(checkIngredient);
					}
					
				}
			}
			$scope.connectedIngredients = ingredientList;
		};
		
		$scope.initializeCanvas = function(){
			$scope.canvas = document.createElement("canvas");
			$scope.context = $scope.canvas.getContext("2d");		
			var container = jQuery("#container");
			container.append($scope.canvas);
			var canvasWrapper=jQuery($scope.canvas);
			canvasWrapper.css("position","absolute").css("top","0px").css("left","0px");
			container.css("position","relative");
			container.css("z-index",1);
			canvasWrapper.css("z-index",0);
		}

		$scope.drawLines = function(){
			var container = jQuery("#container");
			$scope.canvas.width = container.width();
			$scope.canvas.height = container.height();			
			$scope.context.clearRect(0, 0, $scope.canvas.width, $scope.canvas.height);			
			var ctx = $scope.context;
			
		    ctx.beginPath();	
		    var recipeContainer = jQuery("#recipes");
		    var canvasPosition = jQuery($scope.canvas).offset();
			jQuery("#selectedIngredients li").each(function(index){
		    	var ingredientNode = jQuery(this); //the HTML node
		    	var ingredientEntity = $scope.selectedIngredients[index].value; //the data entity from the database
				var ingredientPosition = ingredientNode.offset();
				
				
				var beginX = ingredientPosition.left - canvasPosition.left + ingredientNode.width();
				var beginY = ingredientPosition.top - canvasPosition.top + ingredientNode.height()/2;		
				
				for (var index=0; index < $scope.recipes.length; index++){
					var checkRecipe = $scope.recipes[index];
					var ingredientIsUsedInRecipe = false;
					for (var index2 = 0; index2 < checkRecipe.recipeUsesIngredients.length; index2++){
						var checkRui = checkRecipe.recipeUsesIngredients[index2];
						if (checkRui.ingredient.id == ingredientEntity.id){
							ingredientIsUsedInRecipe = true;
						}
					}
					
					if (ingredientIsUsedInRecipe){
						var recipeNode = recipeContainer.find("#recipe-" + checkRecipe.id);
						var recipePosition = recipeNode.offset();
						
						var endX = recipePosition.left - canvasPosition.left;
						var endY  = recipePosition.top - canvasPosition.top + recipeNode.height()/2;
						
						ctx.moveTo(beginX,beginY);
						ctx.lineTo(endX,endY);
					}
				}				
				
			});
			//draw lines from each recipe node to it's connected ingredient node
			var recipeNodes = jQuery("#recipes li");
			var connectedIngredientsContainer = jQuery("#connectedIngredients");
			for (var index=0; index < $scope.recipes.length; index++){
				var recipe = $scope.recipes[index];
				var recipeNode = jQuery(recipeNodes[index]);
				var recipePosition = recipeNode.offset();
				console.log(recipePosition);
				var beginX = recipePosition.left - canvasPosition.left + recipeNode.width();
				var beginY = recipePosition.top - canvasPosition.top + recipeNode.height() /2;
				
				for (var ruiIndex=0; ruiIndex < recipe.recipeUsesIngredients.length; ruiIndex++){
					var rui = recipe.recipeUsesIngredients[ruiIndex];
					var ingredient = rui.ingredient;
					var ingredientNode = connectedIngredientsContainer.find("#ingredient-" + ingredient.id);
					if (ingredientNode.length==0){
						console.log("failed to find " + ingredient.id);
					}
					var ingredientPosition = ingredientNode.offset();
					
					var endX = ingredientPosition.left - canvasPosition.left;
					var endY = ingredientPosition.top - canvasPosition.top + ingredientNode.height()/2;
					ctx.moveTo(beginX,beginY);
					ctx.lineTo(endX, endY);
				} 
			}			
		    ctx.stroke();  
			
		}
				
		$scope.formatColumns = function(){
			var container = jQuery("#container");
			var width = container.width();
			var maxQuantity = Math.max($scope.selectedIngredients.length, $scope.recipes.length);
			var maxHeight = maxQuantity * 60; //total pixel height space alloted
			
			
			var ingredientNodes = jQuery("#selectedIngredients li");
			slice = maxHeight / ingredientNodes.length;
			ingredientNodes.each(function(index){
				jQuery(this).css("top", (slice*index) + "px");
				
			});			

			var recipeNodes = jQuery("#recipes li");
			var slice = maxHeight / recipeNodes.length;
			recipeNodes.each(function(index){								
				jQuery(this).css("top", (slice*index) + "px");
				jQuery(this).css("left","350px");	
			});
			
			var connectedIngredientNodes = jQuery("#connectedIngredients li");
			var slice = maxHeight / connectedIngredientNodes.length; 
			connectedIngredientNodes.each(function(index){
				jQuery(this).css("top", (slice*index)+ "px");
				jQuery(this).css("left", "650px");
			});
			
			container.css("height",(maxHeight+60) + "px");
		};


		$scope.getMyIngredients = function(){
			$http.get("rest/ingredients/myIngredients").
			success(function(data, status, headers, config) {
				$scope.$watch("selectedIngredients", $scope.analyzeIngredients,true);	
				$scope.$watch("threshold", $scope.analyzeIngredients);
				$scope.myIngredients = data;
			});
		}		
		
		$scope.getClass = function(ingredient){
			for (var index=0; index < $scope.selectedIngredients.length; index++){
				var checkIngredient = $scope.selectedIngredients[index].value;
				if (checkIngredient.id==ingredient.id){
					return "inSelected";
				}
			}
			return "notInSelected";
		};
		
		$scope.initializeCanvas();
		$scope.getMyIngredients();
		
	});
</script>
<style>
	#container ul
	{
		list-style-type: none;
	}
	#selectedIngredients li, #recipes li, #connectedIngredients li
	{
		position: absolute;
		width: 150px;
		hieght: 50px;
		border: 1px solid black;
		
	}
	#connectedIngredients li.inSelected
	{
		border: 2px solid green;
	}
	#connectedIngredients li.notInSelected
	{
		border: 2px dotted red;
	}
	
	#container
	{
		width: 100%;
		position: relative;
	}
</style>

<div ng-controller="analyzeController">
	<selector items="myIngredients" selected-items="selectedIngredients"></selector>
	<div>
		<label for="threshold">Threshold:</label>
		<numeric field-name="threshold"></numeric>
	
	</div>
	<div id="container" ng-class="notInSelected" >
		<ul id="selectedIngredients">
			<li ng-repeat = "ingredient in selectedIngredients">
				{{ingredient.label}}
			</li>
		</ul>
		<ul id="recipes">
			<li ng-repeat = "recipe in recipes" ng-attr-id="{{ 'recipe-' + recipe.id }}">
				{{recipe.title}}
			</li>
		</ul>
		<ul id="connectedIngredients">
			<li  ng-class="getClass(ingredient)" ng-repeat="ingredient in connectedIngredients" ng-attr-id="{{'ingredient-' + ingredient.id}}">			
				{{ingredient.title}}
			</li>
		</ul>
	</div>
</div>
<jsp:include page="footer.jsp"/>