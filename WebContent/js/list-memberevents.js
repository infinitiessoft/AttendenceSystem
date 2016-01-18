angular
		.module('list-memberevents', [ 'ngResource', 'auth' ])
		.controller(
				'list-memberevents',
				[
						'$scope',
						'$http',
						'memberEventService',
						function($scope, $http, memberEventService) {
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

							var queryParams = function(tableState) {
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
								memberEventService
										.list(filters)
										.then(
												function(status) {
													$scope.displayed = status.data.content;
													tableState.pagination.numberOfPages = status.data.totalPages;
													$scope.isLoading = false;
												});
							};

							var update = function(id, entry) {
								$scope.isLoading = true;
								memberEventService
										.update(id, entry)
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

													memberEventService
															.list(filters)
															.then(
																	function(
																			status) {
																		$scope.displayed = status.data.content;
																		$scope.isLoading = false;
																	});
												});
							}

							$scope.permit = function(newsEntry) {
								var entry = {
									"action" : "permit"
								};
								update(newsEntry.id, entry);

							};

							$scope.reject = function(newsEntry) {
								var entry = {
									"action" : "reject"
								};
								update(newsEntry.id, entry);

							};
						} ]).factory(
				'memberEventService',
				[
						'auth',
						'$http',
						function(auth, $http) {
							var serviceBase = 'rest/employees/'
									+ auth.user.principal.id + '/events/';
							var obj = {};
							obj.list = function(queries) {
								return $http.get(serviceBase, {
									params : queries
								});
							};

							obj.get = function(id) {
								return $http.get(serviceBase + id);
							};

							obj.update = function(id, record) {
								return $http.put(serviceBase + id, record);
							};

							return obj;
						} ]);