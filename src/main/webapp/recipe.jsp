

<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/ingredientDialog.js"></script>
<script type="text/javascript" src="resources/js/directives/measurementDialog.js"></script>
<script type="text/javascript">


app.controller('RecipesController', function($scope, $http, $timeout) {

	var id = parseLocation()["recipeId"];	
	$scope.recipe = {
			recipeUsesIngredients: []			
	};
	$scope.recipe.id = id;	
	$scope.ingredient = {};
	$scope.measurement ={};	
	$scope.getRecipe = function(){
		$http.get("rest/recipes/"  + $scope.recipe.id).success(function(data){ $scope.recipe = data;});
	}
	if ($scope.recipe.id){
		$scope.getRecipe();
	}
	$scope.submit = function(){
		var submitText = angular.toJson($scope.recipe);	
		$http({
		    url: "rest/recipes",
		        method: $scope.recipe.id == null ? "POST" : "PUT",		        
		        data: submitText,
		    }).success(function(data, status, headers, config) {
		       
		    	var word = $scope.recipe.id == null ? "created":"modified";
		        alert("The recipe has been successfully " + word);
		        $scope.recipe = data;
		        $scope.getRecipe();
		    });
	};
	
	$scope.getMeasurements= function(){
		$http.get("rest/measurements").
		success(function(data,status,headers,config){
			$scope.measurements = data;			
		});
	};

	$scope.configureRuiAutoCompletes=function(){		
		jQuery("#rui tr").each(function(index,value){
			var row = this;
			var quantityInput = jQuery(this).find(".quantity");
			quantityInput.spinner({
				min: 1,
				max:99,
				change: function(event,ui){
					$scope.recipe.recipeUsesIngredients[index].quantity = jQuery(this).val();
				},
			});
			
			var measurementInput = jQuery(this).find(".measurement-display");
			measurementInput.autocomplete({
				source: $scope.measurements,
				select: function(event,ui){						
					event.preventDefault();
					jQuery(this).val(ui.item.label);
					$scope.recipe.recipeUsesIngredients[index].measurementtype.id=ui.item.value;
					$scope.recipe.recipeUsesIngredients[index].measurementtype.title = ui.item.label;
				},
				lookupFilter: function (suggestion, originalQuery, queryLowerCase) {
                	return suggestion.value.toLowerCase().indexOf(queryLowerCase) === 0; //match only the beginning                	
            	},
                change: function (event, ui) {
                    if(!ui.item){
                        //http://api.jqueryui.com/autocomplete/#event-change -
                        // The item selected from the menu, if any. Otherwise null
                        //so clear the item for force selection
                        jQuery(this).val("");
                    }

                }
			});
			
			
			var ingredientInput = jQuery(this).find(".ingredient-display");
			ingredientInput.autocomplete({
				source:"rest/ingredients",
				select: function(event,ui){
					event.preventDefault();
					jQuery(this).val(ui.item.label);
					$scope.recipe.recipeUsesIngredients[index].ingredient.id=ui.item.value;
					$scope.recipe.recipeUsesIngredients[index].ingredient.title=ui.item.label;
				},
                change: function (event, ui) {
                    if(!ui.item){
                        jQuery(this).val("");
                    }

                }
			});//end autocomplete					
			
		});//end each
	}
	
	$scope.addRui = function(){
		if (!$scope.recipe.recipeUsesIngredients){
			$scope.recipe.recipeUsesIngredients = new Array();
		}
		var newRui = new Object();
		newRui.quantity = 1;
		$scope.recipe.recipeUsesIngredients.push(newRui);
		$timeout(function(){
			//have to delay this so that the digest cycle has time to create the row and inputs
			$scope.configureRuiAutoCompletes();
		});//end timeout
	};//end addRui function
	
	$scope.deleteRui = function(rui){			
		var index=$scope.recipe.recipeUsesIngredients.indexOf(rui);
		$scope.recipe.recipeUsesIngredients.splice(index,1);
		//http://forums.asp.net/t/1899823.aspx?Jquery+UI+Tooltip+remains+on+screen+when+dynamic+content+is+removed
		jQuery(".ui-tooltip-content").parents('div').remove(); //ensure tooltips of deleted rows are removed.				
	};
	
	$scope.setTooltips = function(){
		jQuery(document).tooltip();		
	}
	
	$scope.addMeasurement = function(){
		jQuery("#Measurement").dialog("open");
	}
	
	$scope.addIngredient = function(){		
		jQuery("#ingredientDialog").dialog("open");
	}
	
	$scope.getMeasurements();
	$timeout(function(){
		$scope.configureRuiAutoCompletes();
		$scope.setTooltips();
	});
	
	$scope.valid = function(){
		if ($scope.recipe.title == null || $scope.recipe.instructions == null){
			console.log("A");
			return false;
		}else{
			for (var index=0; index<$scope.recipe.recipeUsesIngredients.length; index++){
				var rui=$scope.recipe.recipeUsesIngredients[index];
				if (rui == null || rui.ingredient == null || rui.measurementtype == null || rui.quantity == null){
					console.log("B " + index);
					return false;
				}
			}
		}
		return true;
	}
});
</script>
<style>
#recipe .ui-icon{
	display: inline-block;
}
</style>

<form ng-controller="RecipesController" >
	<input ng-if = "recipe.id" ng-model="id" name="id" type = "hidden" />
	<div>
		<label for="title">Title</label>
		<input ng-model="recipe.title" name="title" type="text">
	</div>
	<div>
		<label for="instructions">Instructions</label>
		<textarea ng-model="recipe.instructions"></textarea>
	</div>
	<div id="ingredients">
	<H3>Uses Ingredients</H3>
		<table border="1" id="recipe">
			<thead>
				<tr>
					<th>Quantity</th>
					<th>Measurement
						<a ng-click="addMeasurement()" title="Add New Measurement" class="ui-icon ui-icon-plus"></a>
					</th>
					<th>Ingredient
						<a ng-click="addIngredient()" title="Add New Ingredient" class="ui-icon ui-icon-plus"></a>
					</th>
					<th class="icons">
						<a ng-click="addRui()" title="Attach Another Ingredient to this Recipe" class="ui-icon ui-icon-plus"></a>
					</th>
				</tr>
			</thead>
			<tbody id="rui" ng-repeat="rui in recipe.recipeUsesIngredients">
				<tr>
					<td>
						<input class="rui-id" ng-model="rui.id" ng-if="rui.id" ng-show="false"/>
						<input class="quantity" ng-model="rui.quantity" length="2"/></td>
					<td>
						<input class="measurement-display"  ng-model="rui.measurementtype.title"/>
						<input class="measurement-id" ng-model="rui.measurementtype.id" type="hidden"/>
					</td>
					<td>
						<input class="ingredient-display" ng-model="rui.ingredient.title"/>
						<input class="ingredient-id" ng-model="rui.ingredient.id" type="hidden" />												
					</td>
					<td class="icons">						
						<a ng-click="deleteRui(rui)" title="Remove this ingredient from this recipe" class="ui-icon ui-icon-minus"></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<button ng-click="submit()" ng-disabled="!valid()" value="SAVE">SAVE</button>
	<measurement-dialog measurement="measurment"></measurement-dialog>
	<ingredient-dialog ingredient="ingredient"></ingredient-dialog>
	
</form>

<jsp:include page="footer.jsp"/>