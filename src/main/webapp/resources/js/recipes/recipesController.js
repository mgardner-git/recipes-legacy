var app = angular.module('recipesApp');
var id = parseLocation()["id"];

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
			$scope.$apply();
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
					$scope.$apply(function(){
						var ingredient = ui.item.value;
						$scope.recipe.recipeUsesIngredients[index].ingredient=ingredient;
						event.preventDefault();
						inputNode.val(ui.item.label);
						});
					
				},
				change: function(event,ui){
					//if the user types something in, but it doesn't match to anything
					if (!ui.item){
						$scope.$apply(function(){
							var ingredient = {
									title: inputNode.val(),
									description: ""
							};
							$scope.recipe.recipeUsesIngredients[index].ingredient = ingredient;
							/**
							TODO: This still leaves open the possibility that the user has typed in an exact match to another ingredient not knowing that he has to select it from the menu.
							*/
						});
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
