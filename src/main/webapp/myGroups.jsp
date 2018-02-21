<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directives/confirmDeleteRecipeDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directives/joinGroupDialog.js"></script>

<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	app.controller('myGroupsController', function($scope, $http, $timeout,$uibModal) {
		$scope.selectedGroup = null;
		$scope.getMyGroups = function(){
			var result = $http.get("rest/groups/myGroups");
			result.then(function(response) {
				$scope.myGroups = response.data;
			});
		};
		

		
		$scope.openMembershipDialog = function() {
			$uibModal.open({
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
		
			
		}
		
		$scope.getMyGroups();
	});
</script>



<div ng-controller="myGroupsController">
	<h2>My Groups</h2>
	<table id="myGroups" border="1">
		<thead><tr>
			<th>ID</th><th>Title</th>
			<th>Description</th>
			<th align="right"><a class="ui-icon ui-icon-create" ng-click="openMembershipDialog()" title="Join a new Group"></a></th>
		</tr></thead>
	  	<tbody>
	  		<tr ng-repeat="group in myGroups">
	  			<td>{{group.id}}</td>
	  			<td>{{group.title}}</td>
	  			<td>{{group.description.substring(0,255)}}
	  			<td>
	  				<span class="ui-icon ui-icon-large ui-icon-delete" ng-click="confirmDeleteMembership(group)" title="Leave this group."></span>
	  			</td>
	  		</tr>
	  	</tbody>
	</table>
	
	

</div>
<jsp:include page="footer.jsp"/>