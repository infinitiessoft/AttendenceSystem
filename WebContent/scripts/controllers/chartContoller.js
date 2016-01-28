'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description # MainCtrl Controller of the sbAdminApp
 */
angular.module('sbAdminApp').controller(
		'ChartCtrl',
		[
				'$scope',
				'$timeout',
				function($scope, $timeout) {
					$scope.line = {
						labels : [ 'January', 'February', 'March', 'April',
								'May', 'June', 'July' ],
						series : [ 'Series A', 'Series B' ],
						data : [ [ 65, 59, 80, 81, 56, 55, 40 ],
								[ 28, 48, 40, 19, 86, 27, 90 ] ],
						onClick : function(points, evt) {
							console.log(points, evt);
						}
					};

					$scope.bar = {
						labels : [ 'Records', 'Events' ],
						series : [ 'Permit', 'Pending', 'Reject' ],
						data : [ [ 65, 59, ], [ 28, 48 ], [ 86, 27 ] ]

					};

					$scope.donut = {
						labels : [ "Download Sales", "In-Store Sales",
								"Mail-Order Sales" ],
						data : [ 300, 500, 100 ]
					};

					$scope.radar = {
						labels : [ "Pending", "Permit", "Reject" ],

						data : [ [ 65, 59, 90 ], [ 28, 48, 40 ] ]
					};

					$scope.pie = {
						labels : [ "Download Sales", "In-Store Sales",
								"Mail-Order Sales" ],
						data : [ 300, 500, 100 ]
					};

					$scope.polar = {
						labels : [ "Download Sales", "In-Store Sales",
								"Mail-Order Sales", "Tele Sales",
								"Corporate Sales" ],
						data : [ 300, 500, 100, 40, 120 ]
					};

					$scope.dynamic = {
						labels : [ "Download Sales", "In-Store Sales",
								"Mail-Order Sales", "Tele Sales",
								"Corporate Sales" ],
						data : [ 300, 500, 100, 40, 120 ],
						type : 'PolarArea',

						toggle : function() {
							this.type = this.type === 'PolarArea' ? 'Pie'
									: 'PolarArea';
						}
					};
				} ]);