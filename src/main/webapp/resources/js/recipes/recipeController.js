var app = angular.module('recipesApp');
var id = parseLocation()["id"];

app.controller('recipeController', function($scope, $http,$timeout, $uibModal) {
	
	//a two dimensional array of strings representing problems that will prevent the user from saving changes to the recipe.
	$scope.errors = new Array();
	
	$scope.loadMeasurementTypes = function(){
		$http.get("rest/measurementTypes").
		then(function(response){
			$scope.measurementTypes = response.data;
		});
	};
	
	$scope.loadRecipe = function(){
		$http.get("rest/recipes/" + id).
		then(function(response) {
			$scope.recipe = response.data
			
		});			
	};
	
	$scope.showErrors = function(row,column) {
		var errorReference = $scope.errors[row][column];
		var show = typeof(errorReference) != "undefined"
			&& errorReference != null
			&& errorReference != "";
		return show; 
	}
	 
	$scope.submitDisabled = function(){
		if ($scope.recipe != null){
			var recipeInBadState = false;
			for (var index=0; index < $scope.recipe.recipeUsesIngredients.length; index++){			
				var checkRui = $scope.recipe.recipeUsesIngredients[index];
				$scope.errors[index] = new Array();
				
				if (checkRui.quantity == null || checkRui.quantity < 1){
					$scope.errors[index][0] = "Quantity must be greater than 0";
					recipeInBadState = true;
				}
				if (checkRui.measurementType == null || checkRui.measurementType.id == null){
					$scope.errors[index][1] = "You must select a measurement type";
					recipeInBadState =  true;
				}
				if (checkRui.ingredient == null || checkRui.ingredient.id == null){
					$scope.errors[index][2] = "You must select an ingredient";
					recipeInBadState =  true;
				}				
			}
			if ($scope.recipe.title == null || $scope.recipe.title.length == 0){
				recipeInBadState = true;
			}
			return recipeInBadState;			
		}else{
			return true;
		}
	}
	
	
	$scope.lookupMeasurementTypes = function(val){
		var results = new Array();
		for (var index=0; index < $scope.measurementTypes.length; index++){
			var checkMeasurement = $scope.measurementTypes[index];
			//prefix check			
			var prefix = checkMeasurement.title.substring(0,val.length);
			if (prefix.toUpperCase() == val.toUpperCase()){
				results.push(checkMeasurement);
			}			
		}
		return results;
	}
	//used by the typeahead (autocomplete)
	$scope.lookupIngredients = function(val){
		return $http.get("rest/ingredients",{
			params:{
				term:val
			}
		}).then(function(response){
			return response.data; 
		});
	}
	
	
	$scope.openAddMeasurementTypeDialog = function(){
		var modalInstance = $uibModal.open({
			animation:true,
			templateUrl: "resources/dialogs/measurementType.html",
			controller: "measurementTypeDialog",
			resolve: {
				
			}
		});
		modalInstance.result.then(function(measurementType){
			$scope.loadMeasurementTypes();
		})
	};
	
	$scope.openAddIngredientDialog = function(){
		var modalInstance = $uibModal.open({
			animation:true,
			templateUrl:"resources/dialogs/ingredient.html",
			controller: "ingredientDialog",
			resolve:{
				//??
			}				
		});
		modalInstance.result.then(function(ingredient){
			if (ingredient){

			}
		})
	}
	
	$scope.addRui = function(){
		$scope.recipe.recipeUsesIngredients.push({"quantity":0});
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
