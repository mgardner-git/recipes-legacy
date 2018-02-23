
var app = angular.module('recipesApp');
app.controller('myGroupsController', function($scope, $http, $timeout,$uibModal) {
	$scope.selectedGroup = null;
	$scope.getMyGroups = function(){
		var result = $http.get("rest/groups/myGroups");
		result.then(function(response) {
			$scope.myGroups = response.data;
		});
	};
	

	$parentScope = $scope;
	 $scope.openMembershipDialog = function() {
		var modalWindow = $uibModal.open({
			templateUrl: "resources/dialogs/membershipDialog.html",
			backdrop: true,
			windowClass: "modal",
			controller: function($scope, $uibModal, $log) {
				$scope.getGroups = function() {
					var result = $http.get("rest/groups/unjoinedGroups");
					result.then(function(response) {
						$scope.unjoinedGroups = response.data;
					});
				};
				$scope.submitJoinGroup = function() {
					var result = $http.put("rest/memberships?groupId=" + $scope.selectedGroup.id);
					result.then(function(response) {
						alert ("You have joined " + $scope.selectedGroup.title);
						modalWindow.close("");
						$parentScope.getMyGroups(); //parent scope
					});
				}
				$scope.selectGroup = function(index) {
					$scope.selectedGroup = $scope.unjoinedGroups[index];
				}
				$scope.getGroups();
			
			}
		});
	};
	
	$scope.confirmDeleteMembership = function(group){
		$scope.selectedGroup = group;
		var $parentScope = $scope;
		var modalWindow = $uibModal.open({
			templateUrl: "resources/dialogs/confirmDeleteMembershipDialog.html",
			backdrop: true,
			windowClass: "modal",
			controller: function($scope, $uibModal, $log) { 
				$scope.group = $parentScope.selectedGroup;
				$scope.leave = function() {
					var result = $http.delete("rest/memberships?groupId=" + $scope.group.id);
					result.then(function(response) {
						alert("You have left " + $scope.group.title);
						$parentScope.getMyGroups();
					});	
					modalWindow.close();	
				};
				
				$scope.cancel = function() {
					modalWindow.close();
				};
			}
		});
		
	
	};
	
	$scope.getMyGroups();
});