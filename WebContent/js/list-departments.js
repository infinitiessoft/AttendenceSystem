angular.module('list-departments', [ 'ngResource' ]).controller(
		'list-departments',
		[
				'$scope',
				'$http',
				'departmentService',
				function($scope, $http, departmentService) {
					$http.get('rest/auth/').success(function(data) {
						$scope.user = data.name;
					});
					$scope.rowCollection = [];

					var query = function() {
						departmentService.list().then(
								function(status) {
									$scope.rowCollection = status.data;
									$scope.displayedCollection = []
											.concat($scope.rowCollection);
								});
					}

					query();

					$scope.removeEntry = function(newsEntry) {
						if (confirm("Are you sure to delete department: "
								+ newsEntry.name) == true) {
							departmentService.remove(newsEntry.id).then(
									function(status) {
										query();
									});

						}
					};
				} ]);