var app = angular.module("recipesApp");
app.controller('myRecipesController', function($scope, $http, $timeout, $uibModal) {
	$scope.selectedRecipe = null;
	$scope.getMyRecipes = function(){
		console.log("Fetching my recipes");
		$http.get("rest/recipes/myRecipes").
		then(function(response) {
			$scope.myRecipes = response.data;
		});
	}
	$scope.confirmDeleteRecipe = function(recipe){
		var modalDialog = $uibModal.open({
			templateUrl: "resources/dialogs/confirmDeleteRecipe.html",
			backdrop: true,
			windowClass: "modal",
			controller: function($scope, $uibModal, $log) {
				$scope.recipe = recipe;
				$scope.confirmDelete = function() {
					var result = $http.delete("rest/recipes/" + $scope.recipe.id);
					result.then(function(response) {
						alert("You have deleted " + $scope.recipe.title);
					});		
					modalDialog.close("");
				};
				$scope.cancel = function() {
					modalDialog.close("");
				}
			}	
		});
		
	}
	
	$scope.getMyRecipes();
});
