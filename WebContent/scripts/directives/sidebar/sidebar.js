'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description # adminPosHeader
 */

angular.module('sbAdminApp').directive('sidebar', [ '$location', function() {
	return {
		templateUrl : 'scripts/directives/sidebar/sidebar.html',
		restrict : 'E',
		replace : true,
		scope : {},
		controller : function($scope, auth) {
			$scope.selectedMenu = 'dashboard';
			$scope.collapseVar = 0;
			$scope.multiCollapseVar = 0;

			$scope.check = function(x) {

				if (x == $scope.collapseVar)
					$scope.collapseVar = 0;
				else
					$scope.collapseVar = x;
			};

			$scope.multiCheck = function(y) {

				if (y == $scope.multiCollapseVar)
					$scope.multiCollapseVar = 0;
				else
					$scope.multiCollapseVar = y;
			};
			
			$scope.user = function() {
				return auth.user;
			};

			$scope.authenticated = function() {
				return auth.authenticated;
			};
			
			$scope.hasRole = function(role) {

				if ($scope.user() === undefined) {
					return false;
				}

				if ($scope.user().authorities === undefined) {
					return false;
				}

				for (var i = 0; i < $scope.user().authorities.length; i++) {
					if (angular.equals($scope.user().authorities[i].authority, role)) {
						return true;
					}
				}

				return false;
			};
		}
	}
} ]);
