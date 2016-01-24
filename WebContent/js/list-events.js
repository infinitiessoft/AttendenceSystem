angular
		.module('list-events', [])
		.controller(
				'list-events',
				[
						'$scope',
						'$http',
						'eventService',
						function($scope, $http, eventService) {
							var lastState = {
								pagination : {
									start : 0,
									number : 10
								},
								sort : {
									predicate : 'id',
									reverse : true
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
								eventService
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
								eventService
										.update(id, entry)
										.then(
												function(status) {
													var filters = queryParams(lastState);

													eventService
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
						} ]);