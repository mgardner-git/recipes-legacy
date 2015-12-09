app.directive("ingredientDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/ingredient.html",
		scope:{			

		},
		controller: function($scope,$http){			
			$scope.measurementType = {};
		},
		link: function($scope, element, $watch, $http){
			jQuery("#ingredientDialog").dialog({
				autoOpen: false,
				title: "Ingredient",
				modal: true,
				buttons:[
					{
						text: "Create a new Ingredient",
						icons: {primary: "ui-icon-plusthick"},
						click: function() {
							var submitText = angular.toJson($scope.ingredient);
							var callback = function(){
								jQuery("#ingredientDialog").dialog("close");
							}
							var url = "rest/ingredients";							
							jQuery.ajax ({
							    url: url,
							    type: "POST",
							    data: submitText,
							    dataType: "json",
							    contentType: "application/json; charset=utf-8",
							    success: callback							    
							});							
						}
					}
				]
			}); //end dialog
		
		}//end link
	};
});