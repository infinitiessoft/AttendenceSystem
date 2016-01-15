angular.module('list-employees', [ 'ngResource' ]).controller(
		'list-employees',
		[
				'$scope',
				'$http',
				'employeeService',
				function($scope, $http, employeeService) {
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
						employeeService
								.list(filters)
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

												employeeService
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
						'employeeService',
						[
								'$http',
								function($http) {
									var serviceBase = 'rest/employee/';
									var obj = {};
									obj.list = function(queries) {
										return $http.get(serviceBase, {params:queries});
									};

									obj.get = function(id) {
										return $http.get(serviceBase  + id);
									};

									obj.insert = function(employee) {
										return $http.post(serviceBase, employee);
									};

									obj.update = function(id, employee) {
										return $http.put(serviceBase  + id,
												employee).then(function(results) {
											return results;
										});
									};
									
									obj.remove = function(id) {
										return $http.delete(serviceBase + id).then(function(status) {
											return status;
										});
									};

									return obj;
								} ]).factory(
										'employeeRoleService',
										[
												'$http',
												function($http) {
													var serviceBase = 'rest/employee/';
													var obj = {};
													obj.list = function(employeeid) {
														return $http.get(serviceBase+employeeid+"/roles");
													};

													obj.get = function(employeeid,roleid) {
														return $http.get(serviceBase+employeeid+"/roles/"+roleid);
													};

													obj.insert = function(employeeid,roleid) {
														return $http.put(serviceBase+employeeid+"/roles/"+roleid);
													};
													
													obj.remove = function(id) {
														return $http.delete(serviceBase+employeeid+"/roles/"+roleid);
													};

													return obj;
												} ]);