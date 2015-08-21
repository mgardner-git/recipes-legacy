app.directive("measurementDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/measurement.html",
		scope:true,
		controller: function($scope,$http, $timeout){			
			$timeout(function(){
				jQuery("#Measurement").dialog({
					autoOpen: false,
					title: "Add Measurement Type",
					modal: true,
					buttons:[
						{
							text: "Create",
							icons: {primary: "ui-icon-plusthick"},
							click: function() {
								var submitText = angular.toJson($scope.measurementtype);					
								$http({
								    url: "rest/measurements",
								        method: "POST",		        
								        data: submitText,
								    }).success(function(data, status, headers, config) {								        
								        jQuery("#Measurement").dialog("close");
								        $scope.getMeasurements(); //??
								    }).error(function(data, status, headers, config) {
								       alert("Error: " + data);
								});
							}
						}
					]
				});
			});	
		},
		link: function($scope, element, $watch, $http){
			

		}//end link
	};
});