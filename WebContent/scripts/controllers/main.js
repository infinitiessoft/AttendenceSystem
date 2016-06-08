'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('MainCtrl', 
		  [
		   	'$scope',
		   	'$http',
		   	'memberLeaveService',
		   function($scope, $http, memberLeaveService) {
		   		var params = {'typeName':'annual'};
		   		memberLeaveService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.annual = days;
		   					}
		   			);
		   		var params = {'typeName':'personal'};
		   		memberLeaveService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.personal = days;
		   					}
		   			);
		   		var params = {'typeName':'sick'};
		   		memberLeaveService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.sick = days;
		   					}
		   			);
		   		var params = {'typeName':'others'};
		   		memberLeaveService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.others = days;
		   					}
		   			);
		   		
  }]);
