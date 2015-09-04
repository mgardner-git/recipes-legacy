<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/ingredientDialog.js"></script>
<script type="text/javascript" src="resources/js/directives/ingredientGroupDialog.js"></script>
<script type="text/javascript">

	app.controller('ingredientsController', function($scope, $http) {
		
		$scope.myIngredients={};
		$scope.selectedIngredient = {};
		$scope.getMyIngredients = function(){		
			var url ="rest/ingredients/myIngredients";
			if ($scope.term){
				url += "?term=" + term;
			}
			$http.get(url).
			success(function(data, status, headers, config) {		
				$scope.myIngredients = data;
			}).
			error(function(data, status, headers, config) {
				if (status == '<%=HttpServletResponse.SC_FORBIDDEN%>'){
					window.location.href="login.jsp";
				}
				alert("Error: " + data + status + headers);	
			});	
		}
		$scope.watch("term",function(){
			$scope.getMyIngredients();
		});
		$scope.availableGroups = [];
		$scope.scenario=1;
		
		$scope.openDiscussionDialog = function(ingredient){
			$scope.selectedIngredient = ingredient;
			$scope.scenario=3;
			$http.get("rest/groups/ingredientsAndGroups/" + ingredient.id).
			success(function(data,status,headers,config){
				$scope.availableGroups=data;
				jQuery("#ingredientGroupDialog").dialog("open");

			});
		};		
		$scope.selectIngredient = function(ingredient){
			$scope.selectedIngredient = ingredient;	
			jQuery("#ingredientDialog").dialog("open");
		};
		$scope.update = $scope.getMyIngredients;
		$scope.getMyIngredients();
	});
</script>
	<div ng-controller="ingredientsController">
	
	<input class="search" ng-model="term"/>
	
	<table id="ingredients" border="1">
		<thead><tr>
			<th>ID</th><th>Title</th><th>Description</th><th></th>
		</tr></thead>
	  	<tbody>
			<tr ng-repeat="ingredient in myIngredients">	  		
				<td>{{ingredient.id}}</td>
				<td>{{ingredient.title}}</td>
				<td>{{ingredient.description}}</td>
				<td>
					<label class="ui-icon-large ui-icon-edit" tooltip="edit ingredient" ng-click="selectIngredient(ingredient)"></label>
				
					<label class="ui-icon-large ui-icon-discuss" tooltip="Discuss Ingredient with others"
					ng-click="openDiscussionDialog(ingredient)"></label>
				</td>
			</tr>
		</tbody>
	</table>
	<ingredient-dialog ingredient = "selectedIngredient"></ingredient-dialog>
	<ingredient-group-dialog ingredient="selectedIngredient" groups="availableGroups" ></ingredient-group-dialog>
	</div>
<jsp:include page="footer.jsp"/>

