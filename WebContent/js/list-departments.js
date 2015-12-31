angular
		.module('list-departments', [ 'ngResource' ])
		.controller(
				'list-departments',
				[
						'$scope',
						'$http',
						'departmentService',
						function($scope, $http, departmentService) {
							var lastState = {
								pagination : {
									start : 0,
									number : 10
								},
								sort : {
									predicate : 'id',
									reverse : false
								},
								search : {
									predicateObject : {}
								}
							};
							
							var queryParams = function(tableState){
								var pagination = tableState.pagination;
								var start = pagination.start || 0;
								var pageSize = pagination.number || 10;
								var sort = tableState.sort.predicate;
								var dir = tableState.sort.reverse ? 'DESC'
										: 'ASC';
								var predicate = tableState.search.predicateObject;
								var page = (start / pageSize);
								if (page < 0) {
									page = 0;
								}
								lastState.pagination.start = start;
								lastState.pagination.number = pageSize;
								lastState.sort.predicate = sort;
								lastState.sort.reverse = tableState.sort.reverse;
								lastState.search = tableState.search;
								var filters = tableState.search.predicateObject
										|| {};
								filters.sort = sort;
								filters.pageSize = pageSize;
								filters.page = page;
								filters.dir = dir;
								return filters;
							}
							
							$scope.displayed = [];
							$scope.callServer = function callServer(tableState) {
								$scope.isLoading = true;
								
								var filters = queryParams(tableState);
								departmentService
										.list(filters)
										.then(
												function(status) {
													$scope.displayed = status.data.content;
													tableState.pagination.numberOfPages = status.data.totalPages;
													$scope.isLoading = false;
												});
							};

							$scope.removeEntry = function(newsEntry) {
								if (confirm("Are you sure to delete department: "
										+ newsEntry.name) == true) {
									$scope.isLoading = true;
									departmentService
											.remove(newsEntry.id)
											.then(
													function(status) {
														var pagination = lastState.pagination;
														console.log(tableState);
														var start = pagination.start || 0;
														var pageSize = pagination.number || 10;
														var sort = tableState.sort.predicate;
														var dir = tableState.sort.reverse ? 'DESC'
																: 'ASC';
														var page = (start / pageSize);
														if (page < 0) {
															page = 0;
														}

														var filters = queryParams(lastState);

														departmentService
																.list(filters)
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