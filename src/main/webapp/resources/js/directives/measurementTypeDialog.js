app.directive("measurementTypeDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/measurementType.html",
		scope:{			
			updateFunc: "&"
		},
		controller: function($scope,$http){			
			$scope.measurementType = {};
		},
		link: function($scope, element, $watch, $http){
			jQuery("#measurementTypeDialog").dialog({
				autoOpen: false,
				title: "Measurement Type",
				modal: true,
				buttons:[
					{
						text: "Create a new Measurement Type",
						icons: {primary: "ui-icon-plusthick"},
						click: function() {							
							
							var submitText = angular.toJson($scope.measurementType);
							var callback = function(){
								jQuery("#measurementTypeDialog").dialog("close");									
								$scope.updateFunc();
							}
							var url = "rest/measurementTypes";
							
							jQuery.ajax ({
							    url: url,
							    type: "POST",
							    data: submitText,
							    dataType: "json",
							    contentType: "application/json; charset=utf-8",
							    success: callback							    
							});							
						}
					}
				]
			}); //end dialog
		
		}//end link
	};
});