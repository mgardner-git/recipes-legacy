<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/selector.js"></script>
<script type="text/javascript" src="resources/js/directives/ng-size.js"></script>
<script type="text/javascript">
	
	var app = angular.module('recipesApp');
	app.controller('analyzeController', function($scope, $http, $timeout) {
		$scope.myIngredients = [];
		$scope.selectedIngredients=[];
		$scope.getMyIngredients = function(){
			$http.get("rest/ingredients/myIngredients").
			success(function(data, status, headers, config) {
				$scope.myIngredients = data;
			});
		}
		
		$scope.getMyIngredients();
	});
</script>

<div ng-controller="analyzeController">
	<selector items="myIngredients" selection="selectedIngredients"></selector>

</div>
<jsp:include page="footer.jsp"/>