var app = angular.module("recipesApp");
app.controller('myRecipesController', function($scope, $http, $timeout) {
	$scope.selectedRecipe = null;
	$scope.getMyRecipes = function(){
		console.log("Fetching my recipes");
		$http.get("rest/recipes/myRecipes").
		then(function(response) {
			$scope.myRecipes = response.data;
		});
	}
	$scope.confirmDeleteRecipe = function(recipe){
		$scope.selectedRecipe = recipe;
		$timeout(function(){
			jQuery("#confirmDeleteRecipeDialog").dialog("open");	
		});
		
	}
	
	$scope.getMyRecipes();
});
