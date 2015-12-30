angular.module('list-employees', [ 'ngResource' ]).controller(
		'list-employees',
		[
				'$scope',
				'$http',
				'employeeService',
				function($scope, $http, employeeService) {
					var lastPage = 0;
					var lastPageSize = 10;
					var lastSort = 'id';
					var lastDir = 'ASC';

					$scope.displayed = [];
					$scope.callServer = function callServer(tableState) {
						$scope.isLoading = true;
						var pagination = tableState.pagination;

						var start = pagination.start || 0;
						var pageSize = pagination.number || 10;
						var sort = tableState.sort.predicate;
						var dir = tableState.sort.reverse ? 'DESC'
								: 'ASC';
						var page = (start / pageSize);
						if (page < 0) {
							page = 0;
						}
						lastPage = page;
						lastPageSize = pageSize;
						lastSort = sort;
						lastDir = dir;
						employeeService
								.list(page, pageSize, sort, dir)
								.then(
										function(status) {
											$scope.displayed = status.data.content;
											tableState.pagination.numberOfPages = status.data.totalPages;
											$scope.isLoading = false;
										});
					};

					$scope.removeEntry = function(newsEntry) {
						if (confirm("Are you sure to delete employee: "
								+ newsEntry.name) == true) {
							$scope.isLoading = true;
							employeeService
									.remove(newsEntry.id)
									.then(
											function(status) {
														employeeService
														.list(
																lastPage,
																lastPageSize,
																lastSort,
																lastDir)
														.then(
																function(
																		status) {
																	$scope.displayed = status.data.content;
																	$scope.isLoading = false;
																});
											});

						}
					};
				} ]);