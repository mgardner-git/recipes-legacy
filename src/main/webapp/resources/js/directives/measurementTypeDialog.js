var app = angular.module("recipesApp");
app.controller("measurementTypeDialog",function($scope, $uibModalInstance){
	$scope.measurementType = {};
	$scope.cancel=function(){
		$uibModalInstance.close();
	}
		
	$scope.save = function(){
		var submitText = angular.toJson($scope.measurementType);
		var url = "rest/measurementTypes";			
		jQuery.ajax ({
		    url: url,
		    type: "POST",
		    data: submitText,
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(){
		    	$uibModalInstance.close($scope.measurementType);		    	
		    }							    
		});	
		
	}
});