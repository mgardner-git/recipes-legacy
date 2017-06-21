/*! angular-number-spinner - v0.1.3 - 2014-11-05 
Source Code: https://github.com/halilb/angular-number-spinner */ 

angular.module('number-spinner', [])

.constant('State', {
    OVER: 'over',
    BELOW: 'below',
    NORMAL: 'normal'
})

.directive('numberSpinner', ['State',
    function (State) {
        return {
            restrict: 'A',
            templateUrl: '/recipes/resources/js/directives/templates/number-spinner.html',
            replace: true,
            scope: {
                number: '=ngModel',
                changed: '&ngChange',
                max: '=',
                min: '=',
                stateChanged: '&'
            },
            link: function (scope) {
                var doNotTriggerWatch = false;

                function changeNumber(newValue) {
                    scope.number = newValue;
                    if (angular.isFunction(scope.changed)) {
                        scope.changed();
                    }
                }

                function updateState(_state, oldValue) {
                    changeNumber(oldValue);

                    doNotTriggerWatch = _state !== State.NORMAL;

                    if (angular.isFunction(scope.stateChanged)) {
                        scope.stateChanged({
                            state: _state,
                            oldValue: oldValue
                        });
                    }
                }

                scope.$watch('number', function (newValue, oldValue) {
                    if (!doNotTriggerWatch && newValue) {
                        if (typeof newValue === 'string') {
                            newValue = parseInt(newValue.replace(/\D/g, ''), 10);
                        }

                        if ((!scope.max || newValue <= scope.max) && 
                        	(!scope.min || newValue >= scope.min)) {
                            updateState(State.NORMAL, newValue);
                        } else if (scope.max && newValue > scope.max) {
                            updateState(State.OVER, oldValue);
                        } else {
                            updateState(State.BELOW, oldValue);
                        }
                    }
                });

                scope.increase = function () {
                    if (!scope.max || scope.number < scope.max) {
                        updateState(State.NORMAL, scope.number + 1);
                    } else {
                        updateState(State.OVER, scope.number);
                    }
                };

                scope.decrease = function () {
                    if (!scope.min || scope.number > scope.min) {
                        updateState(State.NORMAL, scope.number - 1);
                    } else {
                        updateState(State.BELOW, scope.number);
                    }
                };
            }
        };
    }
]);

