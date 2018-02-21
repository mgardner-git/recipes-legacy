app.directive("joinGroupDialog",function(){
	return{ 
		restrict: "E",
		templateUrl: "resources/dialogs/joinGroupDialog.html",
		scope:{			
			updateFunc: "&"
		},
		controller: function($scope,$http){			
			$scope.group = null;
			$scope.groups = [];
		},
		link: function($scope, element, $watch, $http){
		
				
				jQuery("#joinGroupDialog").dialog({
					autoOpen: false,
					title: "Join Group",
					modal: true,
					buttons:[
						{
							text: "Join the selected Group",
							icons: {primary: "ui-icon-plusthick"},
							click: function() {							
								var callback = function(){
									jQuery("#JoinGroupDialog").dialog("close");
									$scope.updateFunc();
								}
								var url = "rest/memberships?groupId=" + $scope.group.id;
								
								var result = $http.post(url, {});
								result.then(function(response) {
									alert("You have joined " + group.title);
								});
								
													
							}
						}
					]
				}); //end dialog

		}//end link
	};
});