app.directive("selector",function(){
	return{ 
		restrict: "E",
		templateUrl:"js/directives/selector.html",
		scope:{
			source: "=",  //A list of elements for the user to choose from
			destination:"=" //The list where those elemtns should go when selected.
		},
		link: function($scope, element, attrs){

		} ,
		controller: function($scope){
			$scope.filterElements = function(element){
				if (element.title.search($scope.pattern) >= 0){
					return true;
				}
			}
			$scope.moveToDestination = function(element){
				var index=$scope.source.indexOf(element);
				if (index >= 0){
					$scope.source.splice(index,1);
				}
				$scope.destination.push(element);
			}
			$scope.moveToSource = function(element){
				var index= $scope.destination.indexOf(element);
				if (index >= 0){
					$scope.destination.splice(index,1);
				}
				$scope.source.push(element);
			}
		}
	};
});