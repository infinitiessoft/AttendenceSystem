angular.module('list-records', [ 'ngResource' ]).controller(
		'list-records',
		[
				'$scope',
				'$http',
				'recordService',
				'reportService',
				function($scope, $http, recordService,reportService) {
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
					
					var forEachSorted = function(obj, iterator, context) {
					    var keys = sortedKeys(obj);
					    for (var i = 0; i < keys.length; i++) {
					        iterator.call(context, obj[keys[i]], keys[i]);
					    }
					    return keys;
					};

					var sortedKeys = function(obj) {
					    var keys = [];
					    for (var key in obj) {
					        if (obj.hasOwnProperty(key)) {
					            keys.push(key);
					        }
					    }
					    return keys.sort();
					};

					var buildUrl = function(url, params) {
					    if (!params) return url;
					    var parts = [];
					    forEachSorted(params, function (value, key) {
					        if (value == null || value == undefined) return;
					        if (angular.isObject(value)) {
					            value = angular.toJson(value);
					        }
					        parts.push(encodeURIComponent(key) + '=' + encodeURIComponent(value));
					    });
					    return url + ((url.indexOf('?') == -1) ? '?' : '&') + parts.join('&');
					};
					
					
					var serviceBase = 'rest/reports/records';
					$scope.exportHref = buildUrl(serviceBase,lastState.search.predicateObject);
					
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
						
						$scope.exportHref = buildUrl(serviceBase,lastState.search.predicateObject);
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
					
					$scope.exportToCsv=function(){ 
						var filters = queryParams(lastState);
						reportService.downloadRecords(filters).
						success(function(data, status, headers, config) {
							 if (window.navigator.msSaveOrOpenBlob) {
								   var blob = new Blob([decodeURIComponent(encodeURI(data))], {
								     type: "text/csv;charset=utf-8;"
								   });
								   navigator.msSaveBlob(blob, 'records.csv');
								 } else {
								   var a = document.createElement('a');
								   a.href = 'data:attachment/csv;charset=utf-8,' + encodeURI(data);
								   a.target = '_blank';
								   a.download = 'records.csv';
								   document.body.appendChild(a);
								   a.click();
								 }

						  });
			        }
				} ])
				.factory(
						'recordService',
						['$http', function($http) {
									var serviceBase = 'rest/records/';
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
									
									obj.export = function(queries) {
										return $http.get(serviceBase+'export', {params:queries});
									};

									return obj;
								} ])
								
								.factory(
						'reportService',
						['$http', function($http) {
									var serviceBase = 'rest/reports/';
									var obj = {};
									obj.downloadRecords = function(queries) {
										return $http.get(serviceBase+'records', {params:queries});
									};

									return obj;
								} ]);