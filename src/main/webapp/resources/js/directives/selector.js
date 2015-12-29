app.directive("selector",function(){
	return{ 
		restrict: "E",
		templateUrl:'resources/js/directives/templates/selector.html',
		scope:{
			items:"=",
			selection: "="
		},
		link: function($scope, element, attrs){
		} ,
		controller: function($scope, $timeout){
			$scope.selectedItems = [];
			$scope.selectableItems = [];
			
			$scope.$watch("items", function(){
				if ($scope.items.length > 0){
					$scope.selectableItems = [];
					$scope.selectedItems = [];
					for (var index=0; index < $scope.items.length; index++){
						$scope.selectableItems.push($scope.items[index]);
					}
					$scope.selectedItem = null;
				}
				if ($scope.items.length > 20){
					$scope.size = 20;					
				}else{
					$scope.size=$scope.items.length;
				}				

			},true);
			
			$scope.selectAll = function(){
				$scope.selectableItems = [];
				$scope.selectedItems=[];
				for (var index=0; index < $scope.items.length; index++){
					$scope.selectedItems.push($scope.items[index]);
				}
			};
			
			$scope.deselectAll = function(){
				$scope.selectableItems = [];
				$scope.selectedItems=[];
				for (var index=0; index < $scope.items.length; index++){
					$scope.selectableItems.push($scope.items[index]);
				}				
			};
			
			$scope.select = function(){
				var index = $scope.selectableItems.indexOf($scope.selectedItem);
				if (index  > -1){
					$scope.selectableItems.splice(index,1);
					$scope.selectedItems.push($scope.selectedItem);
					$scope.deselectedItem = null;				}
			};
			

			
			$scope.deselect = function(){
				var index = $scope.selectedItems.indexOf($scope.deselectedItem);
				if (index > -1){
					$scope.selectedItems.splice(index,1);
					$scope.selectableItems.push($scope.deselectedItem);
					$scope.selectedItem = null;
				}				
			};
			

			
		}
	};
});