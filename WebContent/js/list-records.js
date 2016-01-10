angular.module('list-records', [ 'ngResource' ]).controller(
		'list-records',
		[
				'$scope',
				'$http',
				'recordService',
				function($scope, $http, recordService) {
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
						recordService
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
							recordService
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

												recordService
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
						'recordService',
						[
								'$http',
								function($http) {
									var serviceBase = 'rest/record/';
									var obj = {};
									obj.list = function(queries) {
										return $http.get(serviceBase, {params:queries});
									};

									obj.get = function(id) {
										return $http.get(serviceBase  + id);
									};

									obj.insert = function(record) {
										return $http.post(serviceBase, record);
									};

									obj.update = function(id, record) {
										return $http.put(serviceBase  + id,
												record);
									};
									
									obj.remove = function(id) {
										return $http.delete(serviceBase + id);
									};

									return obj;
								} ]);