app.directive("dateTime",function(){
	return{ 
		restrict: "E",
		template:'<input type="text" class="{{cssClass}}" tabIndex="{{tabIndex}}" placeholder="{{placeholder}}" ng-blur="blur()"></input>',
		scope:{
			dateVal: "=",
			cssClass: "@",
			tabIndex: "@",
			blur: "&",
			fieldName: "@",
			placeholder: "@"
		},
		link: function($scope, element, attrs){
			var inputNode = jQuery(element).find("input");
			inputNode.datetimepicker({
				dateFormat: 'dd-mm-yy',
				timeFormat: "hh:mm tt",
				showSecond: null,
				showMicrosecond: false,
				showTimezone: false,
			    onSelect: function(date,item){
			    	if(date !== item.lastVal){
			    		jQuery(this).change();
			    		var date = jQuery(inputNode).datepicker("getDate");
			    		$scope.dateVal=date.getTime();
			    	}
			    }
			});
		} ,
		controller: function($scope){

		}
	};
});