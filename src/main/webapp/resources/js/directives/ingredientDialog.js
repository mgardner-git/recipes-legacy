app.directive("ingredientDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/ingredient.html",
		scope:{
			ingredient: "=",
			onUpdate: "&"
		},
		controller: function($scope,$http){			
			$scope.$watch("ingredient", function(){
				jQuery("#ingredientDialog").dialog({
					autoOpen: false,
					title: "Ingredient",
					modal: true,
					buttons:[
						{
							text: ($scope.ingredient && $scope.ingredient.id) ? "Edit " + $scope.ingredient.title : "Create Ingredient",
							icons: {primary: "ui-icon-plusthick"},
							click: function() {
								var submitText = angular.toJson($scope.ingredient);	
								$http({
								    url: "rest/ingredients",
								        method: $scope.ingredient.id ? "PUT":"POST",		        
								        data: submitText,
								    }).success(function(data, status, headers, config) {								        
								        jQuery("#ingredientDialog").dialog("close");								        
								    });
							}
						}
					]
				}); //end dialog
			}); //end watch	
		},
		link: function($scope, element, $watch, $http){
			

		}//end link
	};
});