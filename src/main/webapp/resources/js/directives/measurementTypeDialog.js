var app = angular.module("recipesApp");
app.controller("measurementTypeDialog",function($scope, $uibModalInstance, $http){
	$scope.measurementType = {};
	$scope.cancel=function(){
		$uibModalInstance.close();
	};
		
	$scope.save = function(){
		var submitText = angular.toJson($scope.measurementType);
		var url = "rest/measurementTypes";	
		
		var result = $http.post(url, submitText);
		result.then(function(response) {
			alert("Measurement type " + $scope.measurementType.title + " created");
			$uibModalInstance.close($scope.measurementType);	
		});
	};
});