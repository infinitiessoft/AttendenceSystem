angular
		.module('list-employeeleaves', [ 'ngResource' ])
		.controller(
				'list-employeeleaves',
				[
						'$scope',
						'$http',
						'employeeleaveService',
						function($scope, $http, employeeleaveService) {
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
								employeeleaveService
										.list(filters)
										.then(
												function(status) {
													$scope.displayed = status.data.content;
													tableState.pagination.numberOfPages = status.data.totalPages;
													$scope.isLoading = false;
												});
							};

							$scope.removeEntry = function(newsEntry) {
								if (confirm("Are you sure to delete employeeleave: "
										+ newsEntry.name) == true) {
									$scope.isLoading = true;
									employeeleaveService
											.remove(newsEntry.id)
											.then(
													function(status) {
														var pagination = lastState.pagination;
														var start = pagination.start || 0;
														var pageSize = pagination.number || 10;
														var sort = lastState.sort.predicate;
														var dir = lastState.sort.reverse ? 'DESC'
																: 'ASC';
														var page = (start / pageSize);
														if (page < 0) {
															page = 0;
														}

														var filters = queryParams(lastState);

														employeeleaveService
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
						} ]).factory(
								'employeeleaveService',
								[
										'$http',
										function($http) {
											var serviceBase = 'rest/employeeleave/';
											var obj = {};
											obj.list = function(queries) {
												return $http.get(serviceBase, {params:queries});
											};

											obj.get = function(id) {
												return $http.get(serviceBase  + id);
											};
											
											obj.insert = function(employeeleave) {
												return $http.post(serviceBase, employeeleave).then(
														function(results) {
															return results;
														});
											};

											obj.update = function(id, employeeleave) {
												return $http.put(serviceBase  + id,
														employeeleave).then(function(results) {
													return results;
												});
											};
											
											obj.remove = function(id) {
												return $http.delete(serviceBase + id).then(function(status) {
													return status;
												});
											};

											return obj;
										} ]);