
var app = angular.module('app', ['ngNumberSpin']);

app.controller('Example1Controller', ['$scope', function($scope){

    var vm  = this;

    vm.amount = 10;

}]);

app.controller('Example2Controller', ['$scope', function($scope){

    var vm  = this;

    vm.amount = 10;

    vm.step = 4;

}]);

app.controller('Example3Controller', ['$scope', function($scope){

    var vm  = this;

    vm.amount = 10;

    vm.showButtons = false;

}]);