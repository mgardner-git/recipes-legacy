app.directive("recipeGroupDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/recipeGroupDialog.html",
		scope: false,
		controller: function($scope,$http){			
			
			$scope.selectedGroup = null;
			$scope.selectGroup = function(group){$scope.selectedGroup = group;};
			
			
			$scope.joinGroup = function(group){
				$scope.selectedGroup = group;

				
				$http.post("rest/membership/" + $scope.selectedGroup.id).
				success(function(data,status,headers,config){					
					$scope.openRecipeGroupDialog($scope.selectedRecipe); //refresh the dialog data
				}).
				error(function(data, status, headers, config) {
					if (status == '<%=HttpServletResponse.SC_FORBIDDEN%>'){
						window.location.href="login.jsp";
					}					
				});	
			}
			
			$scope.postRecipe = function(group){
				$scope.selectedGroup = group;
				$http.post("rest/threads/postRecipe?groupId=" + group.id + "&recipeId=" + $scope.selectedRecipe.id).
				success(function(data,status,headers,config){
					$scope.openRecipeGroupDialog($scope.selectedRecipe); //refresh the dialog data
				});
			}

		},
		link: function($scope, element, $watch, $http){
				jQuery("#recipeGroupDialog").dialog({
					autoOpen: false,
					title: "Recipe and Groups",
					modal: true,
					open: function( event, ui ) 
					{
						$scope.selectedGroup = null;
					}
				}); //end dialog
				
		}//end link
	};
});