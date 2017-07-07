var parseLocation = function() {
	var pairs = window.location.search.substring(1).split("&");
	var obj = {};
	var pair;
	var i;

	for ( i in pairs ) {
		if ( pairs[i] === "" ) continue;
			pair = pairs[i].split("=");
			obj[ decodeURIComponent( pair[0] ) ] = decodeURIComponent( pair[1] );
		}
	return obj;
};
	
	
var app = angular.module("recipesApp", ["ngNumberSpin", "ngAnimate", "ui.bootstrap"]);
app.controller("recipesController", function($scope, $http) {
});

//Http Interceptor for generic error handling
app.factory('myHttpResponseInterceptor',['$q','$location',function($q,$location){
	return {
		responseError: function(response){
			//see ErrorHandlerFilter.java
	        if (response.status === 403) {
	            window.location.href="/recipes/login.jsp";
	        }else{
	        	alert("We were unable to process your request: " + response.message);
	        }
	        return $q.reject(response);
	   
		}
	}
}]);


app.config(function($httpProvider) {
	$httpProvider.interceptors.push("myHttpResponseInterceptor");
});

jQuery(function() {
	 
  });

