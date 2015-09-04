<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/recipeGroupDialog.js"></script>
<script>
	
	app.controller('RecipesController', function($scope, $http) {
		$scope.myRecipes=[];
		$scope.groupData = [];	
		$scope.getRecipes=function(){		
			$http.get('rest/recipes').
			success(function(data, status, headers, config) {		
				$scope.myRecipes = data;
			});
		}

		$scope.deleteRecipe = function(recipe){
			$http.delete("rest/recipes/" +  recipe.id).
			success(function(data,status,headers,config){
				alert("Delete of recipe #" + recipe.id + " successfull.");
				$scope.getRecipes();
			})
			.error(function(data,status,headers,config){
				//TODO: centralize this error handling
				if (status == '<%=HttpServletResponse.SC_FORBIDDEN%>'){
					window.location.href="login.jsp";
				}
				alert("Error: " + data + status + headers);
			});
		}

		$scope.openRecipeGroupDialog = function(recipe){			
			$scope.selectedRecipe = recipe;
			$http.get("rest/groups/recipesAndGroups/" + $scope.selectedRecipe.id).success(function(data, status, headers, config) {
				$scope.groupData = data;
				
				jQuery("#recipeGroupDialog").dialog("open");					
			});		
		};
		
		$scope.editRecipe = function(recipe){
			window.location.href="recipe.jsp?recipeId=" + recipe.id;
		}
		$scope.getRecipes();
		
		$scope.test="ALPHA!";
	});
</script>

	<div ng-controller="RecipesController">
		
		<input ng-model="pattern" class="search"/>
		<table id="recipes">
			<thead><tr><th>ID</th><th>Title</th><th>Ingredients</th></tr></thead>
				<tbody>
					<tr ng-repeat="recipe in filteredRecipes = myRecipes | filter: pattern">
						<td>{{recipe.id}}</td>
						<td>{{recipe.title}}</td>
						<td>{{recipe.recipeUsesIngredients.length}}</td>
						<td class="icons">
							<a ng-click="deleteRecipe(recipe)" tooltip="Delete Recipe" class="ui-icon-large ui-icon-delete"></a>
							<a ng-click="editRecipe(recipe)" tooltip="Edit Recipe" class="ui-icon-large ui-icon-edit"></a>
							<a ng-click="openRecipeGroupDialog(recipe)" tooltip = "Post Recipe to a Group" class="ui-icon-large ui-icon-discuss"></a>
						</td>	  			
					</tr>
				</tbody>
		</table>
		<recipe-group-dialog test="test" recipe="selectedRecipe" groupData="groupData"></recipe-group-dialog>
				
	</div>
<jsp:include page="footer.jsp"/>
