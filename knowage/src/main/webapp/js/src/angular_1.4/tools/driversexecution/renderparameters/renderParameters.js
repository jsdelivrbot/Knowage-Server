(function() {
	var driversExecutionModule = angular.module('driversExecutionModule');

		driversExecutionModule.directive('renderParameters', ['sbiModule_config', function(sbiModule_config){

			return {
				restrict: 'E',
				templateUrl: sbiModule_config.contextName
					+ '/js/src/angular_1.4/tools/driversexecution/renderparameters/template/renderParameters.html',
				controller: renderParametersController,
				scope: {
					parameter: '=',
					driverableObject: '=',
				}
			}
		}]);

		var renderParametersController = function($scope, sbiModule_config, sbiModule_translate,driversExecutionService){

			$scope.sbiModule_translate = sbiModule_translate;
			$scope.driverableObject = $scope.driverableObject;

		}

	})();