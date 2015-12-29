app.directive('ngSize', function(){
	return {
		restrict: 'A',
		link: function(scope,element,attrs){
			if(!element.nodeName === 'SELECT'){
				return;
			}else{
				attrs.$observe('ngSize', function setSize(value){
	                attrs.$set('size', attrs.ngSize);	
				});
			}
		}
	}
})