app.directive("ingredientGroupDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/postIngredientToGroup.html",
		scope:{
			ingredient: "=",
			groups: "=",
			scenario:"="			
		},
		controller: function($scope,$http){			
			$scope.selectedGroup = null;
			$scope.selectGroup = function(group){$scope.selectedGroup = group;};
			
			$scope.joinGroup = function(){
				$http.put("JoinGroupServlet?id=" + groupId).
				success(function(data,status,headers,config){					
					
				}).
				error(function(data, status, headers, config) {
					if (status == '<%=HttpServletResponse.SC_FORBIDDEN%>'){
						window.location.href="login.jsp";
					}					
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