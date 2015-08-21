app.directive("postContent",function(){
	return{ 
		restrict: "E",
		templateUrl: "js/directives/postContent.html",
		scope:{
			post: "="			
		},
		controller: function($scope,$http){	
			if ($scope.post.recipe_fk != null){
				$http.get("RecipeServlet", {params: {recipeId: $scope.post.recipe_fk}}).then(function(response){
					$scope.post.recipe = response.data;
				});
			}
		},
		link: function($scope, element, $watch, $http){
			
		}//end link
	};
});