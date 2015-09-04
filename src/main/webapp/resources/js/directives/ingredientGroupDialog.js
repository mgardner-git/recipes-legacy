app.directive("ingredientGroupDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/ingredientGroupDialog.html",
		scope: false,
		controller: function($scope,$http){			
			$scope.selectedGroup = null;
			$scope.selectGroup = function(group){$scope.selectedGroup = group;};
			
			$scope.joinGroup = function(group){
				
				$http.post("rest/membership/" + group.id).
				success(function(data,status,headers,config){					
					$scope.openDiscussionDialog($scope.selectedIngredient);
				});
			}
			
			$scope.postNewThread = function(group){
				$scope.selectedGroup = group;
				$http.post("rest/threads/postIngredient?groupId=" + group.id + "&ingredientId=" + $scope.selectedIngredient.id).
				success(function(data,status,headers,config){
					$scope.openDiscussionDialog($scope.selectedIngredient); //refresh the dialog data
				});
			}
			
			$scope.view = function(){
				
			}
		},
		link: function($scope, element, $watch, $http){
			
				jQuery("#ingredientGroupDialog").dialog({
					autoOpen: false,
					title: "Ingredient",
					modal: true,
					open: function( event, ui ) 
					{
						$scope.selectedGroup = null;
					}
				}); //end dialog
				
		}//end link
	};
});