angular.module('list-employees', [ 'ngResource' ]).controller(
		'list-employees',
		[
				'$scope',
				'$http',
				'employeeService',
				function($scope, $http, employeeService) {
					$scope.rowCollection = [];

					var query = function() {
						employeeService.list().then(
								function(status) {
									$scope.rowCollection = status.data;
									$scope.displayedCollection = []
											.concat($scope.rowCollection);
								});
					}

					query();

					$scope.removeEntry = function(newsEntry) {
						if (confirm("Are you sure to delete employee: "
								+ newsEntry.name) == true) {
							employeeService.remove(newsEntry.id).then(
									function(status) {
										query();
									});

						}
					};
				} ]);