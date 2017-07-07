var app = angular.module("recipesApp");
app.controller("ingredientDialog",function($scope, $uibModalInstance, $http){
	$scope.ingredient = {};
	$scope.cancel=function(){
		$uibModalInstance.close();
	}
		
	$scope.save = function(){
		var submitText = angular.toJson($scope.ingredient);
		$http({
			method: "POST",
			url: "rest/ingredients",
			data: submitText				
		}).then(function(response){
			alert("Ingredient Saved");
			$uibModalInstance.close();
		});

		
	}
});
