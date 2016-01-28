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
		   	'memberService',
		   function($scope, $http, memberService) {
		   		var params = {'typeName':'annual'};
		   		memberService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.annual = days;
		   					}
		   			);
		   		var params = {'typeName':'personal'};
		   		memberService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.personal = days;
		   					}
		   			);
		   		var params = {'typeName':'sick'};
		   		memberService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.sick = days;
		   					}
		   			);
		   		var params = {'typeName':'others'};
		   		memberService
		   			.list(params)
		   			.then(
		   					function(status) {
		   						console.log(status.data);
		   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
		   						$scope.others = days;
		   					}
		   			);
		   		
  }]);
