var app = angular.module('ngNumberSpin', []);

app.directive('numericOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, modelCtrl) {

            modelCtrl.$parsers.push(function (inputValue) {
                var transformedInput = inputValue ? inputValue.replace(/[^\d.-]/g, '') : null;

                if (transformedInput != inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }

                return transformedInput;
            });
        }
    };
});

app.directive('numberSpin', [function () {

    return {
        restrict: 'E',
        scope: {
            "ngModel": '=',
            "spinButtons": '=?',
            "step": '='
        },
        template: '<div class="{{showSpinButtons ? \'\' : \'no-spin-buttons\'}}">' +
        '<input numeric-only data-ng-model="ngModel" ng-pattern="onlyNumbers" type="text">' +
        '<a class="ns-plus"  data-ng-if="showSpinButtons"  data-ng-click="plus()">+</a>' +
        '<a class="ns-minus" data-ng-if="showSpinButtons"  data-ng-click="minus()">-</a> </div>',
        link: function (scope, elem, attrs) {

            console.log('spinButtons', scope.spinButtons);

            if (scope.spinButtons == false) {
                scope.showSpinButtons = false;
            } else {
                scope.showSpinButtons = true;
            }

            scope.onlyNumbers = /^\d+$/;

            scope.plus = function () {

                if (scope.step) {
                    scope.ngModel = scope.ngModel + scope.step;
                } else {
                    scope.ngModel = scope.ngModel + 1;
                }

            };

            scope.minus = function () {
                if (scope.step) {
                    scope.ngModel = scope.ngModel - scope.step;
                } else {
                    scope.ngModel = scope.ngModel - 1;
                }
            }


        }
    }

}])