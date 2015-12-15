app.directive("confirmDeleteRecipeDialog",function(){
	return{ 
		restrict: "E",
		template: "<div id='confirmDeleteRecipeDialog'></div>",
		scope:{			
			recipe: "=",
			updateFunc: "&"
		},
		controller: function($scope,$http){			
			$scope.measurementType = {};
			console.log($scope.updateFunc);
		},
		link: function($scope, element, $watch, $http){
			$scope.$watch("recipe", function(){
				if ($scope.recipe){
					jQuery("#confirmDeleteRecipeDialog").dialog({
						autoOpen: false,
						title: "Ingredient",
						modal: true,
						buttons:[
							{
								text: "Delete " + $scope.recipe.title,
								icons: {primary: "ui-icon-plusthick"},
								click: function() {							
									var callback = function(){
										jQuery("#confirmDeleteRecipeDialog").dialog("close");
										$scope.updateFunc();
									}
									var url = "rest/recipes/" + $scope.recipe.id;							
									jQuery.ajax ({
									    url: url,
									    type: "DELETE",							    
									    dataType: "json",
									    contentType: "application/json; charset=utf-8",
									    success: callback							    
									});							
								}
							},
							{
								text: "Don't Delete " + $scope.recipe.title,
								icons: {primary:"ui-icon-close"},
								click: function(){
									jQuery("#confirmDeleteRecipeDialog").dialog("close");
								}							
							}
						]
					}); //end dialog
				}
			});//end watch
		
		}//end link
	};
});