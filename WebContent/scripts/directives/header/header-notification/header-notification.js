'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description # adminPosHeader
 */
angular
		.module('sbAdminApp')
		.directive(
				'headerNotification',
				function() {
					return {
						templateUrl : 'scripts/directives/header/header-notification/header-notification.html',
						restrict : 'E',
						replace : true,
						controller : function($scope, auth) {
							$scope.user = function() {
								return auth.user;
							};

							$scope.authenticated = function() {
								return auth.authenticated;
							};
							
							$scope.logout = auth.clear;
						}
					}
				});
