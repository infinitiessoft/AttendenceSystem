angular.module('navigation', [ 'ngRoute', 'auth' ]).controller('navigation',

function($scope, $route, auth) {

	$scope.credentials = {};

	$scope.hasRole = function(role) {

		if ($scope.user() === undefined) {
			return false;
		}

		if ($scope.user().authorities === undefined) {
			return false;
		}

		for (i = 0; i < $scope.user().authorities.length; i++) {
			if (angular.equals($scope.user().authorities[i].authority, role)) {
				return true;
			}
		}

		return false;
	};

	$scope.user = function() {
		return auth.user;
	}

	$scope.authenticated = function() {
		return auth.authenticated;
	}

	$scope.login = function() {
		auth.authenticate($scope.credentials, function(authenticated) {
			if (authenticated) {
				console.log("Login succeeded")
				 $scope.error = false;
			} else {
				console.log("Login failed")
				 $scope.error = true;
			}
		})
	};

	$scope.logout = auth.clear;

});