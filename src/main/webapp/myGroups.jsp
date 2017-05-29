<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/confirmDeleteRecipeDialog.js"></script>

<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	app.controller('myGroupsController', function($scope, $http, $timeout) {
		$scope.selectedGroup = null;
		$scope.getMyGroups = function(){
			$http.get("rest/groups/myGroups").
			success(function(data, status, headers, config) {
				$scope.myGroups = data;
			});
		}
		$scope.confirmDeleteMembership = function(group){
			$scope.selectedGroup = group;
			$timeout(function(){
				jQuery("#confirmDeleteMembershipDialog").dialog("open");	
			});
			
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
			<th align="right"><a ng-href="recipe.jsp" class="ui-icon ui-icon-create" title="Join a new Group"></a></th>
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
	<confirm-delete-membership-dialog group="selectedGroup" update-func="getMyGroups()"></confirm-delete-membership-dialog>
</div>
<jsp:include page="footer.jsp"/>