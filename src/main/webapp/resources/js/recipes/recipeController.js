var app = angular.module('recipesApp');
var id = parseLocation()["id"];

app.controller('recipeController', function($scope, $http,$timeout, $uibModal) {
	
	
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
	
	
	$scope.submitDisabled = function(){
		if ($scope.recipe != null){
			for (var index=0; index < $scope.recipe.recipeUsesIngredients.length; index++){			
				var checkRui = $scope.recipe.recipeUsesIngredients[index];
				if (checkRui.quantity == null || checkRui.quantity == 0){
					return true;
				}
				if (checkRui.measurementType == null || checkRui.measurementType.id == null){
					return true;
				}
				if (checkRui.ingredient == null || checkRui.ingredient.id == null){
					return true;
				}				
			}
			if ($scope.recipe.title == null || $scope.recipe.title.length == 0){
				return true;
			}
			return false;			
		}else{
			return true;
		}
	}
	
	$scope.lookupMeasurementTypes = function(val){
		var results = new Array();
		for (var index=0; index < $scope.measurementTypes.length; index++){
			var checkMeasurement = $scope.measurementTypes[index];
			//prefix check			
			if (checkMeasurement.title.substring(0,val.length) == val){
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
			templateUrl: "/recipes/resources/dialogs/measurementType.html",
			controller: "measurementTypeDialog",
			resolve: {
				//??
			}
		});
		modalInstance.result.then(function(measurementType){
			if (measurementType){
				$scope.measurementTypes.push(measurementType); //??should we reload these?
			}
		})
	};
	
	$scope.openAddIngredientDialog = function(){
		var modalInstance = $uibModal.open({
			animation:true,
			templateUrl:"/recipes/resources/dialogs/ingredient.html",
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
